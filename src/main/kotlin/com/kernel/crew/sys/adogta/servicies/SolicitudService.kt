package com.kernel.crew.sys.adogta.servicies

import com.kernel.crew.sys.adogta.dto.request.SolicitudRequest
import com.kernel.crew.sys.adogta.dto.response.SolicitudResponse
import com.kernel.crew.sys.adogta.entities.AnimalId
import com.kernel.crew.sys.adogta.entities.SolicitudEntity
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

    /**
     * Registra el interés de un adoptante en un animal publicado.
     *
     * Valida que el adoptante no sea el dueño de la publicación,
     * que el animal exista y su publicación esté activa,
     * y que no haya una solicitud previa del mismo adoptante.
     * Si todo es válido, crea la solicitud y notifica al donante por correo.
     *
     * @param token Token de sesión del adoptante.
     * @param request Datos de la solicitud.
     * @return [SolicitudResponse] de la solicitud creada, o null si la sesión es inválida.
     */
    @Transactional
    fun expresarInteres(token: String, request: SolicitudRequest): SolicitudResponse? {
        logger.info("Expresando interés en animal ${request.idAnimal} (publicación ${request.idPublicacion})")

        val usuarioAutenticado = usuarioService.getMe(token)
            ?: run {
                logger.warn("Sesión inválida al expresar interés")
                return null
            }

        val adoptanteId = usuarioAutenticado.id.toInt()

        if (adoptanteId == request.idUsuarioAnimal)
            throw IllegalArgumentException("No puedes expresar interés en tu propia publicación.")

        val animal = animalRepository.findById(
            AnimalId(
                idAnimal = request.idAnimal,
                idPublicacion = request.idPublicacion,
                idUsuario = request.idUsuarioAnimal
            )
        ).orElseThrow { IllegalArgumentException("Animal no encontrado.") }

        if (animal.publicacion.estado != "Activa")
            throw IllegalArgumentException("La publicación ya no está disponible.")

        if (solicitudRepository.existsByIdUsuarioAndIdAnimalAndIdPublicacionAnimalAndIdUsuarioAnimal(
                idUsuario = adoptanteId,
                idAnimal = request.idAnimal,
                idPublicacionAnimal = request.idPublicacion,
                idUsuarioAnimal = request.idUsuarioAnimal
            )
        ) throw IllegalStateException("Ya expresaste interés en esta publicación.")

        val adoptante = usuarioRepository.findById(usuarioAutenticado.id)
            .orElseThrow { RuntimeException("Usuario no encontrado con id: ${usuarioAutenticado.id}") }

        val guardada = solicitudRepository.save(
            SolicitudEntity(
                idUsuario = adoptanteId,
                usuario = adoptante,
                fecha = LocalDate.now(),
                estado = "Pendiente",
                idAnimal = request.idAnimal,
                idPublicacionAnimal = request.idPublicacion,
                idUsuarioAnimal = request.idUsuarioAnimal
            )
        )

        logger.info("Solicitud creada: id=${guardada.idSolicitud}, usuario=$adoptanteId")

        val nombreAdoptante = listOfNotNull(
            adoptante.nombres,
            adoptante.apellidoPaterno,
            adoptante.apellidoMaterno
        ).joinToString(" ").trim()

        emailService.enviarCorreoSolicitudAdopcion(
            destinatario = animal.usuario.email,
            nombreAnimal = animal.nombre,
            nombreAdoptante = nombreAdoptante,
            emailAdoptante = adoptante.email,
            telefonoAdoptante = adoptante.telefono
        )
        logger.info("Notificación de solicitud enviada al donante ${animal.usuario.email}")

        return SolicitudResponse(
            idSolicitud = guardada.idSolicitud,
            idUsuario = adoptanteId,
            idAnimal = guardada.idAnimal,
            idPublicacion = guardada.idPublicacionAnimal,
            idUsuarioAnimal = guardada.idUsuarioAnimal,
            estado = guardada.estado,
            fecha = guardada.fecha
        )
    }
}
