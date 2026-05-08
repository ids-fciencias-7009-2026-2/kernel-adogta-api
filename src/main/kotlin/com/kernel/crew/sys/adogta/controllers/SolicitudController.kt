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

    @PostMapping
    fun expresarInteres(
        @RequestHeader("Authorization", required = false) token: String?,
        @Valid @RequestBody request: SolicitudRequest
    ): ResponseEntity<Any> {
        logger.info("POST /api/solicitudes - animal=${request.idAnimal} publicacion=${request.idPublicacion}")
        if (token == null) {
            logger.warn("POST /api/solicitudes sin token de sesión")
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
        return try {
            val solicitud = solicitudService.expresarInteres(token, request)
            if (solicitud == null) {
                logger.warn("POST /api/solicitudes con token inválido o expirado")
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
            }
            ResponseEntity.status(HttpStatus.CREATED).body(solicitud)
        } catch (e: IllegalStateException) {
            logger.warn("Solicitud duplicada: ${e.message}")
            ResponseEntity.status(HttpStatus.CONFLICT).body(mapOf("error" to e.message))
        } catch (e: IllegalArgumentException) {
            logger.warn("Solicitud inválida: ${e.message}")
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message))
        } catch (e: Exception) {
            logger.warn("Error al crear solicitud: ${e.message}")
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mapOf("error" to e.message))
        }
    }
}
