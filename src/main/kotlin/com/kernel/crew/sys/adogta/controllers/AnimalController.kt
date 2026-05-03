package com.kernel.crew.sys.adogta.controllers

import com.kernel.crew.sys.adogta.dto.request.AnimalRequest
import com.kernel.crew.sys.adogta.servicies.AnimalService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/animales")
@CrossOrigin(origins = ["*"])
class AnimalController(
    private val animalService: AnimalService
) {

    // ruta final
    @PostMapping("/publicar")
    fun publicarAnimal(@RequestBody request: AnimalRequest): ResponseEntity<Any> {
        return try {
            val nuevoAnimal = animalService.publicarAnimal(request)
            // Si todo sale bien, regresa un estado con "¡Creado exitosamente!" y los datos del animal
            ResponseEntity.status(HttpStatus.CREATED).body(nuevoAnimal)
        } catch (e: Exception) {
	    // Si no, lanzamos error
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message))
        }
    }
}