package com.kernel.crew.sys.adogta.servicies

import com.kernel.crew.sys.adogta.dto.request.AnimalRequest
import com.kernel.crew.sys.adogta.entities.AnimalEntity
import com.kernel.crew.sys.adogta.repositories.AnimalRepository
import com.kernel.crew.sys.adogta.repositories.UsuarioRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AnimalService(
    private val animalRepository: AnimalRepository,
    private val usuarioRepository: UsuarioRepository
) {

    @Transactional
    fun publicarAnimal(request: AnimalRequest): AnimalEntity {
        // se busca al user usando el ID
        val usuario = usuarioRepository.findById(request.usuarioId)
            .orElseThrow { RuntimeException("Usuario no encontrado") }

        // se crea el objeto Animal
        val nuevoAnimal = AnimalEntity(
            nombre = request.nombre,
            especie = request.especie,
            edad = request.edad,
            tamano = request.tamano,
            descripcion = request.descripcion,
            fotoUrl = request.fotoUrl,
            publicador = usuario
        )

        // se guarda en la db y lo regresamos
        return animalRepository.save(nuevoAnimal)
    }
}