package com.kernel.crew.sys.adogta.servicies

import com.kernel.crew.sys.adogta.dto.request.AnimalRequest
import com.kernel.crew.sys.adogta.dto.response.AnimalResponse
import com.kernel.crew.sys.adogta.entities.AnimalEntity
import com.kernel.crew.sys.adogta.entities.PublicacionEntity
import com.kernel.crew.sys.adogta.repositories.AnimalRepository
import com.kernel.crew.sys.adogta.repositories.PublicacionRepository
import com.kernel.crew.sys.adogta.repositories.RazaRepository
import com.kernel.crew.sys.adogta.repositories.UsuarioRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AnimalService(
    private val animalRepository: AnimalRepository,
    private val publicacionRepository: PublicacionRepository,
    private val razaRepository: RazaRepository,
    private val usuarioRepository: UsuarioRepository
) {

    @Transactional
    fun publicarAnimal(request: AnimalRequest): AnimalResponse {
        // se busca el usuario
        val usuario = usuarioRepository.findById(request.usuarioId)
            .orElseThrow { RuntimeException("Usuario no encontrado") }

        // se busca la raza
        val raza = razaRepository.findById(request.idRaza)
            .orElseThrow { RuntimeException("Raza no encontrada en el catálogo") }

        // se crea y guarda la publicación
        val nuevaPublicacion = PublicacionEntity(
            usuario = usuario,
            estado = "Activa"
        )
        val publicacionGuardada = publicacionRepository.save(nuevaPublicacion)

        val nuevoAnimal = AnimalEntity(
            publicacion = publicacionGuardada,
            usuario = usuario, // El mismo dueño
            nombre = request.nombre,
            estadoVacunacion = request.estadoVacunacion,
            esterilizado = request.esterilizado,
            descripcion = request.descripcion,
            entrenado = request.entrenado,
            codigoPostal = request.codigoPostal,
            edad = request.edad,
            tipo = request.tipo,
            raza = raza,
            overrideEnergia = request.overrideEnergia,
            overrideIndependencia = request.overrideIndependencia,
            overrideSociableNiños = request.overrideSociableNiños,
            overrideSociableMascotas = request.overrideSociableMascotas
        )
        val animalGuardado = animalRepository.save(nuevoAnimal)

        return AnimalResponse(
            idAnimal = animalGuardado.id,
            idPublicacion = publicacionGuardada.id,
            nombre = animalGuardado.nombre
        )
    }
}