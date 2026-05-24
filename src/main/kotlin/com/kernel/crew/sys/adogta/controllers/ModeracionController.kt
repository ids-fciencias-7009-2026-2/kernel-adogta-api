package com.kernel.crew.sys.adogta.controllers

import com.kernel.crew.sys.adogta.dto.request.BanRequest
import com.kernel.crew.sys.adogta.dto.request.ReporteRequest
import com.kernel.crew.sys.adogta.dto.request.ResolverReporteRequest
import com.kernel.crew.sys.adogta.servicies.ModeracionService
import com.kernel.crew.sys.adogta.servicies.AdministradorService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Controlador REST para las operaciones de moderación (reportes y baneos).
 *
 * Expone los endpoints:
 * Usuarios:
 * - POST /api/reportes 
 * Administradores:
 * - GET  /api/admin/reportes 
 * - PUT  /api/admin/reportes/{idReporte}/resolver 
 * - POST /api/admin/baneos 
 */
@RestController
@RequestMapping("/api")
class ModeracionController(
    private val moderacionService: ModeracionService,
    private val administradorService: AdministradorService
) {

    private val logger = LoggerFactory.getLogger(ModeracionController::class.java)

    /**
     * Crea un reporte sobre una publicación.
     *
     * @param token   Token de sesión del usuario.
     * @param request Datos del reporte (idPublicacion, motivo).
     * @return 201 y reporte creado, 400 si error, 401 sin token.
     */
    @PostMapping("/reportes")
    fun crearReporte(
        @RequestHeader("Authorization", required = false) token: String?,
        @RequestBody request: ReporteRequest
    ): ResponseEntity<Any> {
        logger.info("POST /api/reportes")
        if (token == null) return ResponseEntity.status(401).build()
        return try {
            val reporte = moderacionService.crearReporte(token, request)
            ResponseEntity.status(HttpStatus.CREATED).body(reporte)
        } catch (e: Exception) {
            logger.warn("Error al crear reporte: ${e.message}")
            ResponseEntity.status(400).body(mapOf("error" to e.message))
        }
    }

    /**
     * Lista los reportes pendientes.
     *
     * @param token Token de sesión del administrador.
     * @return 200 y lista de reportes, 403 si no es admin, 401 sin token.
     */
    @GetMapping("/admin/reportes")
    fun listarReportes(
        @RequestHeader("Authorization", required = false) token: String?
    ): ResponseEntity<Any> {
        logger.info("GET /api/admin/reportes")
        if (token == null) return ResponseEntity.status(401).build()
        if (administradorService.validarToken(token) == null) return ResponseEntity.status(403).build()
        return try {
            ResponseEntity.ok(moderacionService.listarReportesParaAdmin())
        } catch (e: Exception) {
            ResponseEntity.status(400).body(mapOf("error" to e.message))
        }
    }

    /**
     * Resuelve un reporte: desestimar o dar de baja la publicación.
     *
     * @param token     Token de sesión del administrador.
     * @param idReporte ID del reporte a resolver.
     * @param request   Acción a tomar ("DESESTIMAR" o "BAJA_PUBLICACION").
     * @return 200 y mensaje de éxito, 403 si no es admin, 401 sin token.
     */
    @PutMapping("/admin/reportes/{idReporte}/resolver")
    fun resolverReporte(
        @RequestHeader("Authorization", required = false) token: String?,
        @PathVariable idReporte: Long,
        @RequestBody request: ResolverReporteRequest
    ): ResponseEntity<Any> {
        logger.info("PUT /api/admin/reportes/{}/resolver", idReporte)
        if (token == null) return ResponseEntity.status(401).build()
        if (administradorService.validarToken(token) == null) return ResponseEntity.status(403).build()
        return try {
            moderacionService.resolverReporte(idReporte, request, token)
            ResponseEntity.ok(mapOf("mensaje" to "Reporte resuelto"))
        } catch (e: Exception) {
            ResponseEntity.status(400).body(mapOf("error" to e.message))
        }
    }

    /**
     * Banea a un usuario.
     *
     * @param token   Token de sesión del administrador.
     * @param request Datos del baneo (idUsuario, motivo).
     * @return 201 y mensaje con el email del usuario baneado, 403 si no es admin, 401 sin token.
     */
    @PostMapping("/admin/baneos")
    fun banearUsuario(
        @RequestHeader("Authorization", required = false) token: String?,
        @RequestBody request: BanRequest
    ): ResponseEntity<Any> {
        logger.info("POST /api/admin/baneos")
        if (token == null) return ResponseEntity.status(401).build()
        if (administradorService.validarToken(token) == null) return ResponseEntity.status(403).build()
        return try {
            val email = moderacionService.banearUsuario(token, request)
            ResponseEntity.status(HttpStatus.CREATED).body(
                mapOf("mensaje" to "Usuario baneado: $email")
            )
        } catch (e: Exception) {
            logger.warn("Error al banear usuario: ${e.message}")
            ResponseEntity.status(400).body(mapOf("error" to e.message))
        }
    }
}