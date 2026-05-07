package com.kernel.crew.sys.adogta.servicies

import com.kernel.crew.sys.adogta.dto.request.AnimalRequest
import com.kernel.crew.sys.adogta.dto.response.AnimalResponse
import com.kernel.crew.sys.adogta.entities.AnimalEntity
import com.kernel.crew.sys.adogta.entities.AnimalId
import com.kernel.crew.sys.adogta.entities.PublicacionEntity
import com.kernel.crew.sys.adogta.entities.PublicacionId
import com.kernel.crew.sys.adogta.repositories.AnimalRepository
import com.kernel.crew.sys.adogta.repositories.PublicacionRepository
import com.kernel.crew.sys.adogta.repositories.RazaRepository
import com.kernel.crew.sys.adogta.repositories.UsuarioRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AnimalService(
    private val animalRepository: AnimalRepository,
    private val publicacionRepository: PublicacionRepository,
    private val razaRepository: RazaRepository,
    private val usuarioRepository: UsuarioRepository,
    private val usuarioService: UsuarioService
) {
    private val logger = LoggerFactory.getLogger(AnimalService::class.java)

    @Transactional
    fun publicarAnimal(token: String, request: AnimalRequest): AnimalResponse? {
        logger.info("Publicando animal: ${request.nombre} (${request.tipo}, raza ${request.idRaza})")

        val usuarioAutenticado = usuarioService.getMe(token)
        if (usuarioAutenticado == null) {
            logger.warn("Sesión inválida al publicar animal")
            return null
        }

        val usuario = usuarioRepository.findById(usuarioAutenticado.id)
            .orElseThrow { RuntimeException("Usuario no encontrado con id: ${usuarioAutenticado.id}") }

        val raza = razaRepository.findById(request.idRaza).orElse(null)
        if (raza == null) {
            logger.warn("Raza no encontrada con id: ${request.idRaza}")
            throw RuntimeException("Raza no encontrada con id: ${request.idRaza}")
        }

        val nuevaPublicacion = PublicacionEntity(
            id = PublicacionId(idUsuario = usuario.id!!.toInt()),
            usuario = usuario,
            estado = "Activa"
        )
        val publicacionGuardada = publicacionRepository.save(nuevaPublicacion)

        val animalId = AnimalId(
            idPublicacion = publicacionGuardada.id!!.idPublicacion,
            idUsuario = usuario.id!!.toInt()
        )

        val nuevoAnimal = AnimalEntity(
            id = animalId,
            publicacion = publicacionGuardada,
            usuario = usuario,
            nombre = request.nombre.trim(),
            estadoVacunacion = request.estadoVacunacion,
            esterilizado = request.esterilizado!!,
            descripcion = request.descripcion.trim(),
            entrenado = request.entrenado,
            codigoPostal = request.codigoPostal,
            edad = request.edad,
            tipo = request.tipo,
            raza = raza,
            overrideEnergia = request.overrideEnergia,
            overrideIndependencia = request.overrideIndependencia,
            overrideSociableNiños = request.overrideSociableNiños,
            overrideSociableMascotas = request.overrideSociableMascotas,
            padecimientos = request.padecimientos.map { it.trim() }.filter { it.isNotEmpty() }.toMutableSet(),
            fotos = request.fotos.map { it.trim() }.filter { it.isNotEmpty() }.toMutableSet()
        )

        val animalGuardado = animalRepository.save(nuevoAnimal)
        logger.info("Animal publicado: id=${animalGuardado.id?.idAnimal}, publicacion=${publicacionGuardada.id?.idPublicacion}")

        return AnimalResponse(
            idAnimal = animalGuardado.id?.idAnimal,
            idPublicacion = publicacionGuardada.id?.idPublicacion,
            nombre = animalGuardado.nombre
        )
    }
}
