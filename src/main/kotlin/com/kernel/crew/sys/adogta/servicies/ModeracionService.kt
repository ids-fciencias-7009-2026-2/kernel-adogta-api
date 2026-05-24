package com.kernel.crew.sys.adogta.servicies

import com.kernel.crew.sys.adogta.dto.request.BanRequest
import com.kernel.crew.sys.adogta.dto.request.ReporteRequest
import com.kernel.crew.sys.adogta.dto.request.ResolverReporteRequest
import com.kernel.crew.sys.adogta.dto.response.ReporteResponse
import com.kernel.crew.sys.adogta.entities.BanEntity
import com.kernel.crew.sys.adogta.entities.ReporteEntity
import com.kernel.crew.sys.adogta.repositories.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

/**
 * Servicio que contiene la lógica de negocio relacionada con la moderación
 * de publicaciones y usuarios (reportes y baneos).
 */
@Service
class ModeracionService(
    private val usuarioService: UsuarioService,
    private val administradorService: AdministradorService,
    private val usuarioRepository: UsuarioRepository,
    private val reporteRepository: ReporteRepository,
    private val banRepository: BanRepository,
    private val publicacionRepository: PublicacionRepository,
    private val animalRepository: AnimalRepository,
    private val emailService: EmailService
) {

    private val logger = LoggerFactory.getLogger(ModeracionService::class.java)

    /**
     * Crea un reporte sobre una publicación.
     *
     * @param token   Token de sesión del usuario que reporta.
     * @param request Datos del reporte (idPublicacion, motivo).
     * @return [ReporteResponse] con los datos del reporte creado.
     * @throws IllegalArgumentException si el token es inválido, la publicación no existe,
     *         se reporta a sí mismo o ya reportó antes.
     */
    @Transactional
    fun crearReporte(token: String, request: ReporteRequest): ReporteResponse {

        val usuario = usuarioService.getAsEntity(token)
            ?: throw IllegalArgumentException("Token inválido")

        val publicacion = publicacionRepository.findByIdPublicacion(request.idPublicacion)
            .firstOrNull()
            ?: throw IllegalArgumentException("Publicación no encontrada")

        if (publicacion.usuario?.id == usuario.id) {
            throw IllegalArgumentException("No puedes reportar tu propia publicación")
        }

        if (reporteRepository.existsByUsuarioIdAndPublicacionId(usuario.id!!, request.idPublicacion)) {
            throw IllegalArgumentException("Ya reportaste esta publicación")
        }

        val reporte = ReporteEntity(
            idUsuario = usuario.id!!,
            idPublicacion = request.idPublicacion,
            idUsuarioPublicacion = publicacion.idUsuario,
            motivo = request.motivo,
            estado = "Pendiente",
            fecha = LocalDate.now()
        )
        val guardado = reporteRepository.save(reporte)

        logger.info("Reporte creado: id=${guardado.idReporte}, publicación=${request.idPublicacion}")

        val animal = animalRepository.findByIdPublicacion(request.idPublicacion).firstOrNull()
        return ReporteResponse(
            idReporte = guardado.idReporte,
            idPublicacion = guardado.idPublicacion,
            nombreAnimal = animal?.nombre ?: "",
            motivo = guardado.motivo,
            fecha = guardado.fecha,
            estado = guardado.estado
        )
    }

    /**
     * Lista todos los reportes en estado "Pendiente".
     *
     * @return Lista de [ReporteResponse].
     */
    @Transactional(readOnly = true)
    fun listarReportesPendientes(): List<ReporteResponse> {
        return reporteRepository.findByEstado("Pendiente").map { reporte ->
            val animal = animalRepository.findByIdPublicacion(reporte.idPublicacion).firstOrNull()
            ReporteResponse(
                idReporte = reporte.idReporte,
                idPublicacion = reporte.idPublicacion,
                nombreAnimal = animal?.nombre ?: "",
                motivo = reporte.motivo,
                fecha = reporte.fecha,
                estado = reporte.estado
            )
        }
    }

    /**
     * Resuelve un reporte: desestimar el reporte o dar de baja la publicación.
     *
     * @param idReporte  ID del reporte a resolver.
     * @param request    Acción a tomar ("DESESTIMAR" o "BAJA_PUBLICACION").
     * @param tokenAdmin Token de sesión del administrador.
     * @throws IllegalArgumentException si faltan argumntos
     */
    @Transactional
    fun resolverReporte(idReporte: Long, request: ResolverReporteRequest, tokenAdmin: String) {
        val admin = administradorService.validarToken(tokenAdmin)
            ?: throw IllegalArgumentException("Token de administrador inválido")

        val reporte = reporteRepository.findById(idReporte)
            .orElseThrow { IllegalArgumentException("Reporte no encontrado") }
        val publicacion = publicacionRepository.findByIdPublicacion(reporte.idPublicacion)
            .firstOrNull()
            ?: throw IllegalArgumentException("Publicación asociada no encontrada")

        when (request.accion) {
            "DESESTIMAR" -> {
                reporte.estado = "Desestimado"
                publicacion.estado = "Activa"
            }
            "BAJA_PUBLICACION" -> {
                reporte.estado = "Atendido"
                publicacion.estado = "Borrada"
            }
            else -> throw IllegalArgumentException("Acción no válida")
        }

        // Registrar quién resolvió el reporte
        reporte.idAdministrador = admin.idAdministrador

        reporteRepository.save(reporte)
        publicacionRepository.save(publicacion)
        logger.info("Reporte $idReporte resuelto por administrador ${admin.email}")
    }

    /**
     * Banea a un usuario: crea el registro de ban, invalida su sesión,
     * oculta sus publicaciones y envía un correo de notificación.
     *
     * @param tokenAdmin Token de sesión del administrador que ejecuta la acción.
     * @param request    Datos del baneo (idUsuario, motivo).
     * @return Email del usuario baneado.
     * @throws IllegalArgumentException si el token es inválido, el usuario no existe
     *         o ya está baneado.
     */
    @Transactional
    fun banearUsuario(tokenAdmin: String, request: BanRequest): String {
        logger.info("Baneo de usuario ${request.idUsuario} por administrador")

        // Validar token de administrador y obtener sus datos
        val admin = administradorService.validarToken(tokenAdmin)
            ?: throw IllegalArgumentException("Token de administrador inválido")

        val usuario = usuarioRepository.findById(request.idUsuario)
            .orElseThrow { IllegalArgumentException("Usuario no encontrado") }
        if (banRepository.existsByUsuarioId(usuario.id!!))
            throw IllegalArgumentException("El usuario ya está baneado")

        val ban = BanEntity(
            usuario = usuario,
            por = "${admin.nombres} ${admin.apellidoPaterno}",
            motivo = request.motivo,
            fecha = LocalDate.now()
        )
        banRepository.save(ban)

        // Invalidar sesión del usuario baneado
        val usuarioActualizado = usuario.copy(tokenSesion = null, fechaExpiracionSesion = null)
        usuarioRepository.save(usuarioActualizado)

        // Ocultar sus publicaciones
        val publicaciones = publicacionRepository.findByIdUsuario(usuario.id!!.toInt())
        publicaciones.forEach { it.estado = "Suspndida" }
        publicacionRepository.saveAll(publicaciones)

        // Enviar correo de notificación
        try {
            emailService.enviarNotificacionBaneo(usuario.email ?: "", request.motivo)
            logger.info("Correo de baneo enviado a ${usuario.email}")
        } catch (e: Exception) {
            logger.error("No se pudo enviar el correo de baneo a ${usuario.email}: ${e.message}")
        }

        return usuario.email ?: "desconocido"
    }
}