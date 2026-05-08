package com.kernel.crew.sys.adogta.controllers

import com.kernel.crew.sys.adogta.dto.request.SolicitudRequest
import com.kernel.crew.sys.adogta.servicies.SolicitudService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/solicitudes")
@CrossOrigin(origins = ["http://localhost:5173"])
class SolicitudController(
    private val solicitudService: SolicitudService
) {

    private val logger = LoggerFactory.getLogger(SolicitudController::class.java)

    /**
     * Crea una nueva solicitud de adopción ("me interesa") para un animal.
     *
     * Endpoint protegido: requiere token válido en el header Authorization.
     *
     * @param token   Token de sesión del adoptante.
     * @param request Datos del animal y publicación de interés.
     * @return 201 con [SolicitudResponse], 401 si el token es inválido, 400 si hay error de negocio.
     */
    @PostMapping
    fun crearSolicitud(
        @RequestHeader("Authorization", required = false) token: String?,
        @Valid @RequestBody request: SolicitudRequest
    ): ResponseEntity<Any> {
        logger.info("POST /api/solicitudes - animal ${request.idAnimal}")

        if (token == null) {
            logger.warn("POST /api/solicitudes sin token de sesión")
            return ResponseEntity.status(401).build()
        }

        return try {
            val solicitud = solicitudService.crearSolicitud(token, request)
            if (solicitud == null) {
                logger.warn("POST /api/solicitudes con token inválido o expirado")
                return ResponseEntity.status(401).build()
            }
            ResponseEntity.status(HttpStatus.CREATED).body(solicitud)
        } catch (e: Exception) {
            logger.warn("Error al crear solicitud: ${e.message}")
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message))
        }
    }

    /**
     * Retorna todas las solicitudes del usuario autenticado.
     *
     * Endpoint protegido: requiere token válido en el header Authorization.
     *
     * @param token Token de sesión del adoptante.
     * @return 200 con lista de solicitudes, 401 si el token es inválido.
     */
    @GetMapping("/mis-solicitudes")
    fun obtenerMisSolicitudes(
        @RequestHeader("Authorization", required = false) token: String?
    ): ResponseEntity<Any> {
        logger.info("GET /api/solicitudes/mis-solicitudes")

        if (token == null) {
            logger.warn("GET /api/solicitudes/mis-solicitudes sin token")
            return ResponseEntity.status(401).build()
        }

        val solicitudes = solicitudService.obtenerMisSolicitudes(token)
            ?: return ResponseEntity.status(401).build()

        return ResponseEntity.ok(solicitudes)
    }
}