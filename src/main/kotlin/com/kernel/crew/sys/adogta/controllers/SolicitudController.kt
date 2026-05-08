package com.kernel.crew.sys.adogta.controllers

import com.kernel.crew.sys.adogta.dto.request.SolicitudRequest
import com.kernel.crew.sys.adogta.servicies.SolicitudService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Controlador REST que expone los endpoints relacionados con la entidad Solicitud.
 *
 * Maneja las operaciones de creación de solicitudes de adopción
 * bajo la ruta base '/api/solicitudes'.
 *
 * Toda la lógica de negocio está delegada a [SolicitudService].
 * Este controlador solo recibe peticiones, llama al service y devuelve respuestas.
 */
@RestController
@RequestMapping("/api/solicitudes")
class SolicitudController {

    private val logger = LoggerFactory.getLogger(SolicitudController::class.java)

    @Autowired
    lateinit var solicitudService: SolicitudService

    /**
     * Registra el interés de un adoptante en un animal publicado.
     *
     * Endpoint protegido: requiere un token válido en el header 'Authorization'.
     *
     * @param token Token de sesión enviado en el header 'Authorization'.
     * @param request Datos de la solicitud.
     * @return 201 con los datos de la solicitud creada,
     *         409 si ya existe una solicitud previa,
     *         400 si los datos son inválidos,
     *         o 401 si el token es nulo, inválido o la sesión expiró.
     */
    @PostMapping
    fun expresarInteres(
        @RequestHeader("Authorization", required = false) token: String?,
        @Valid @RequestBody request: SolicitudRequest
    ): ResponseEntity<Any> {
        logger.info("POST /api/solicitudes - animal=${request.idAnimal} publicacion=${request.idPublicacion}")

        if (token == null) {
            logger.warn("POST /api/solicitudes sin token de sesión")
            return ResponseEntity.status(401).build()
        }

        return try {
            val solicitud = solicitudService.expresarInteres(token, request)
            if (solicitud == null) {
                logger.warn("POST /api/solicitudes con token inválido o expirado")
                return ResponseEntity.status(401).build()
            }
            ResponseEntity.status(201).body(solicitud)
        } catch (e: IllegalStateException) {
            logger.warn("Solicitud duplicada: ${e.message}")
            ResponseEntity.status(409).body(mapOf("error" to e.message))
        } catch (e: IllegalArgumentException) {
            logger.warn("Solicitud inválida: ${e.message}")
            ResponseEntity.status(400).body(mapOf("error" to e.message))
        } catch (e: Exception) {
            logger.warn("Error al crear solicitud: ${e.message}")
            ResponseEntity.status(500).body(mapOf("error" to e.message))
        }
    }
}