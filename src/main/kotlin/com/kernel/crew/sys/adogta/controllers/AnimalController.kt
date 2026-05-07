package com.kernel.crew.sys.adogta.controllers

import com.kernel.crew.sys.adogta.dto.request.AnimalRequest
import com.kernel.crew.sys.adogta.servicies.AnimalService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/animales")
@CrossOrigin(origins = ["http://localhost:5173"])
class AnimalController(
    private val animalService: AnimalService
) {

    private val logger = LoggerFactory.getLogger(AnimalController::class.java)

    @PostMapping("/publicar")
    fun publicarAnimal(
        @RequestHeader("Authorization", required = false) token: String?,
        @Valid @RequestBody request: AnimalRequest
    ): ResponseEntity<Any> {
        logger.info("POST /api/animales/publicar - ${request.nombre} (${request.tipo})")
        if (token == null) {
            logger.warn("POST /api/animales/publicar sin token de sesión")
            return ResponseEntity.status(401).build()
        }
        return try {
            val nuevoAnimal = animalService.publicarAnimal(token, request)
            if (nuevoAnimal == null) {
                logger.warn("POST /api/animales/publicar con token inválido o expirado")
                return ResponseEntity.status(401).build()
            }
            ResponseEntity.status(HttpStatus.CREATED).body(nuevoAnimal)
        } catch (e: Exception) {
            logger.warn("Error al publicar animal: ${e.message}")
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message))
        }
    }
}
