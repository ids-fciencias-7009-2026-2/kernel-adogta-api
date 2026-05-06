package com.kernel.crew.sys.adogta.controllers

import com.kernel.crew.sys.adogta.dto.request.AnimalRequest
import com.kernel.crew.sys.adogta.servicies.AnimalService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/animales")
@CrossOrigin(origins = ["http://localhost:5173"])
class AnimalController(
    private val animalService: AnimalService
) {

    @PostMapping("/publicar")
    fun publicarAnimal(
        @RequestHeader("Authorization", required = false) token: String?,
        @RequestBody request: AnimalRequest
    ): ResponseEntity<Any> {
        if (token == null) return ResponseEntity.status(401).build()
        return try {
            val nuevoAnimal = animalService.publicarAnimal(token, request)
                ?: return ResponseEntity.status(401).build()
            ResponseEntity.status(HttpStatus.CREATED).body(nuevoAnimal)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message))
        }
    }
}
