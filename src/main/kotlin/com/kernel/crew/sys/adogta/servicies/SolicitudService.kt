package com.kernel.crew.sys.adogta.servicies

import com.kernel.crew.sys.adogta.dto.request.SolicitudRequest
import com.kernel.crew.sys.adogta.dto.response.SolicitudResponse
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
    private val usuarioRepository: UsuarioRepository,
    private val animalRepository: AnimalRepository,
    private val usuarioService: UsuarioService
) {

    private val logger = LoggerFactory.getLogger(SolicitudService::class.java)

    /**
     * Crea una nueva solicitud de adopción ("me interesa") para un animal publicado.
     *
     * Valida que el token sea válido, que el animal exista y que
     * el adoptante no haya enviado ya una solicitud para ese animal.
     *
     * @param token   Token de sesión del adoptante.
     * @param request Datos del animal y publicación de interés.
     * @return [SolicitudResponse] con los datos de la solicitud creada, o null si el token es inválido.
     * @throws RuntimeException si el animal no existe o ya hay una solicitud activa.
     */
    @Transactional
    fun crearSolicitud(token: String, request: SolicitudRequest): SolicitudResponse? {
        logger.info("Creando solicitud para animal ${request.idAnimal} en publicación ${request.idPublicacion}")

        val usuarioAutenticado = usuarioService.getMe(token)
        if (usuarioAutenticado == null) {
            logger.warn("Token inválido al crear solicitud")
            return null
        }

        val usuario = usuarioRepository.findById(usuarioAutenticado.id)
            .orElseThrow { RuntimeException("Usuario no encontrado con id: ${usuarioAutenticado.id}") }

        val yaSolicitado = solicitudRepository.existsByIdIdUsuarioAndIdAnimalAndIdPublicacionAnimal(
            idUsuario = usuario.id!!.toInt(),
            idAnimal = request.idAnimal,
            idPublicacionAnimal = request.idPublicacion
        )
        if (yaSolicitado) {
            logger.warn("El usuario ${usuario.id} ya tiene una solicitud para el animal ${request.idAnimal}")
            throw RuntimeException("Ya enviaste una solicitud para este animal.")
        }

        val solicitudId = SolicitudId(idUsuario = usuario.id!!.toInt())

        val nuevaSolicitud = SolicitudEntity(
            id = solicitudId,
            usuario = usuario,
            fecha = LocalDate.now(),
            estado = "Pendiente",
            idAnimal = request.idAnimal,
            idPublicacionAnimal = request.idPublicacion,
            idUsuarioAnimal = request.idUsuarioAnimal
        )

        val solicitudGuardada = solicitudRepository.save(nuevaSolicitud)
        logger.info("Solicitud creada con id=${solicitudGuardada.id?.idSolicitud} para usuario ${usuario.id}")

        return SolicitudResponse(
            idSolicitud = solicitudGuardada.id?.idSolicitud ?: 0,
            fecha = solicitudGuardada.fecha.toString(),
            estado = solicitudGuardada.estado,
            idAnimal = solicitudGuardada.idAnimal,
            idPublicacion = solicitudGuardada.idPublicacionAnimal,
            idUsuarioAnimal = solicitudGuardada.idUsuarioAnimal
        )
    }

    /**
     * Retorna todas las solicitudes enviadas por el usuario autenticado.
     *
     * @param token Token de sesión del adoptante.
     * @return Lista de [SolicitudResponse], o null si el token es inválido.
     */
    fun obtenerMisSolicitudes(token: String): List<SolicitudResponse>? {
        logger.info("Obteniendo solicitudes para token")

        val usuarioAutenticado = usuarioService.getMe(token)
        if (usuarioAutenticado == null) {
            logger.warn("Token inválido al obtener solicitudes")
            return null
        }

        val solicitudes = solicitudRepository.findAllByIdIdUsuario(usuarioAutenticado.id.toInt())
        logger.info("Se encontraron ${solicitudes.size} solicitudes para usuario ${usuarioAutenticado.id}")

        return solicitudes.map { solicitud ->
            SolicitudResponse(
                idSolicitud = solicitud.id?.idSolicitud ?: 0,
                fecha = solicitud.fecha.toString(),
                estado = solicitud.estado,
                idAnimal = solicitud.idAnimal,
                idPublicacion = solicitud.idPublicacionAnimal,
                idUsuarioAnimal = solicitud.idUsuarioAnimal,
                mensaje = ""
            )
        }
    }
}