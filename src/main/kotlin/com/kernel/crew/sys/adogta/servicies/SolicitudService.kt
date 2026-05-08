package com.kernel.crew.sys.adogta.servicies

import com.kernel.crew.sys.adogta.dto.request.SolicitudRequest
import com.kernel.crew.sys.adogta.dto.response.SolicitudResponse
import com.kernel.crew.sys.adogta.entities.AnimalId
import com.kernel.crew.sys.adogta.entities.SolicitudEntity
import com.kernel.crew.sys.adogta.entities.SolicitudId
import com.kernel.crew.sys.adogta.repositories.AnimalRepository
import com.kernel.crew.sys.adogta.repositories.SolicitudRepository
import com.kernel.crew.sys.adogta.repositories.UsuarioRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class SolicitudService(
    private val solicitudRepository: SolicitudRepository,
    private val animalRepository: AnimalRepository,
    private val usuarioRepository: UsuarioRepository,
    private val usuarioService: UsuarioService,
    private val emailService: EmailService
) {
    private val logger = LoggerFactory.getLogger(SolicitudService::class.java)

    @Transactional
    fun expresarInteres(token: String, request: SolicitudRequest): SolicitudResponse? {
        logger.info("Expresando interés en animal ${request.idAnimal} (publicación ${request.idPublicacion})")

        val usuarioAutenticado = usuarioService.getMe(token)
        if (usuarioAutenticado == null) {
            logger.warn("Sesión inválida al expresar interés")
            return null
        }

        val adoptanteId = usuarioAutenticado.id.toInt()

        if (adoptanteId == request.idUsuarioAnimal) {
            throw IllegalArgumentException("No puedes expresar interés en tu propia publicación.")
        }

        val animalId = AnimalId(
            idAnimal = request.idAnimal,
            idPublicacion = request.idPublicacion,
            idUsuario = request.idUsuarioAnimal
        )
        val animal = animalRepository.findById(animalId).orElse(null)
            ?: throw IllegalArgumentException("Animal no encontrado.")

        if (animal.publicacion.estado != "Activa") {
            throw IllegalArgumentException("La publicación ya no está disponible.")
        }

        val yaExiste = solicitudRepository
            .existsByIdIdUsuarioAndIdAnimalAndIdPublicacionAnimalAndIdUsuarioAnimal(
                idUsuario = adoptanteId,
                idAnimal = request.idAnimal,
                idPublicacionAnimal = request.idPublicacion,
                idUsuarioAnimal = request.idUsuarioAnimal
            )
        if (yaExiste) {
            throw IllegalStateException("Ya expresaste interés en esta publicación.")
        }

        val adoptante = usuarioRepository.findById(usuarioAutenticado.id)
            .orElseThrow { RuntimeException("Usuario no encontrado con id: ${usuarioAutenticado.id}") }

        val nueva = SolicitudEntity(
            id = SolicitudId(idUsuario = adoptanteId),
            usuario = adoptante,
            fecha = LocalDate.now(),
            estado = "Pendiente",
            idAnimal = request.idAnimal,
            idPublicacionAnimal = request.idPublicacion,
            idUsuarioAnimal = request.idUsuarioAnimal
        )
        val guardada = solicitudRepository.save(nueva)
        logger.info("Solicitud creada: id=${guardada.id?.idSolicitud}, usuario=$adoptanteId")

        val donante = animal.usuario
        val nombreAdoptante = listOfNotNull(
            adoptante.nombres,
            adoptante.apellidoPaterno,
            adoptante.apellidoMaterno
        ).joinToString(" ").trim()

        emailService.enviarCorreoSolicitudAdopcion(
            destinatario = donante.email,
            nombreAnimal = animal.nombre,
            nombreAdoptante = nombreAdoptante,
            emailAdoptante = adoptante.email,
            telefonoAdoptante = adoptante.telefono
        )
        logger.info("Notificación de solicitud enviada al donante ${donante.email}")

        return SolicitudResponse(
            idSolicitud = guardada.id?.idSolicitud ?: 0,
            idUsuario = adoptanteId,
            idAnimal = guardada.idAnimal,
            idPublicacion = guardada.idPublicacionAnimal,
            idUsuarioAnimal = guardada.idUsuarioAnimal,
            estado = guardada.estado,
            fecha = guardada.fecha
        )
    }
}
