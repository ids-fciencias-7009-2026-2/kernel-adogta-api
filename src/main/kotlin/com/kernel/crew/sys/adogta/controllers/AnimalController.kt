package com.kernel.crew.sys.adogta.controllers

import com.kernel.crew.sys.adogta.dto.request.AnimalRequest
import com.kernel.crew.sys.adogta.servicies.AnimalService
import com.kernel.crew.sys.adogta.dto.request.AnimalUpdateRequest
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Controlador REST que expone los endpoints relacionados con la entidad Animal.
 *
 * Maneja las operaciones de publicación y consulta de animales en adopción
 * bajo la ruta base '/api/animales'.
 */
@RestController
@RequestMapping("/api/animales")
class AnimalController {

    private val logger = LoggerFactory.getLogger(AnimalController::class.java)

    @Autowired
    lateinit var animalService: AnimalService

    /**
     * Lista todos los animales en adopción. Endpoint público.
     *
     * @return 200 con lista de animales, 500 si hay error.
     */
    @GetMapping
    fun listarAnimales(): ResponseEntity<Any> {
        logger.info("GET /api/animales")
        return try {
            ResponseEntity.ok(animalService.listarPublicaciones())
        } catch (e: Exception) {
            logger.warn("Error al listar publicaciones de animales: ${e.message}")
            ResponseEntity.status(500).body(mapOf("error" to e.message))
        }
    }

    /**
     * Obtiene el detalle público de un animal por su ID.
     *
     * @param idAnimal ID del animal a consultar.
     * @return 200 con [com.kernel.crew.sys.adogta.dto.response.AnimalListItemResponse],
     *         o 404 si no existe o la publicación fue borrada.
     */
    @GetMapping("/{idAnimal}")
    fun obtenerDetalleAnimal(@PathVariable idAnimal: Int): ResponseEntity<Any> {
        logger.info("GET /api/animales/{}", idAnimal)
        val animal = animalService.obtenerDetalleAnimal(idAnimal)
            ?: return ResponseEntity.status(404)
                .body(mapOf("error" to "Animal no encontrado con id: $idAnimal"))
        return ResponseEntity.ok(animal)
    }

    /**
     * Publica un nuevo animal en adopción.
     *
     * @param token Token de sesión del usuario.
     * @param request Datos del animal a publicar.
     * @return 201 con datos del animal, 401 si falta el token o es inválido, 400 si hay error.
     */
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
            ResponseEntity.status(201).body(nuevoAnimal)
        } catch (e: Exception) {
            logger.warn("Error al publicar animal: ${e.message}")
            ResponseEntity.status(400).body(mapOf("error" to e.message))
        }
    }

    /**
     * Lista las publicaciones del usuario autenticado.
     *
     * @param token Token de sesión del usuario.
     * @return 200 con lista de publicaciones, 401 si no hay token, 400 si hay error.
     */
    @GetMapping("/mis-publicaciones")
    fun misPublicaciones(
        @RequestHeader("Authorization", required = false) token: String?
    ): ResponseEntity<Any> {
        logger.info("GET /api/animales/mis-publicaciones")
        if (token == null) {
            logger.warn("GET /api/animales/mis-publicaciones sin token")
            return ResponseEntity.status(401).build()
        }
        return try {
            ResponseEntity.ok(animalService.listarMisPublicaciones(token))
        } catch (e: Exception) {
            logger.warn("Error al listar publicaciones propias: ${e.message}")
            ResponseEntity.status(400).body(mapOf("error" to e.message))
        }
    }

    /**
     * Obtiene los datos completos de un animal para su edición.
     * Solo el dueño de la publicación puede acceder.
     *
     * @param token Token de sesión del usuario.
     * @param idAnimal ID del animal a consultar.
     * @return 200 con [AnimalDetailResponse], 401 si no hay token, 400 si hay error.
     */
    @GetMapping("/{idAnimal}/editar")
    fun obtenerParaEditar(
        @RequestHeader("Authorization", required = false) token: String?,
        @PathVariable idAnimal: Int
    ): ResponseEntity<Any> {
        logger.info("GET /api/animales/{}/editar", idAnimal)
        if (token == null) {
            logger.warn("GET /api/animales/{}/editar sin token", idAnimal)
            return ResponseEntity.status(401).build()
        }
        return try {
            ResponseEntity.ok(animalService.obtenerAnimalParaEditar(token, idAnimal))
        } catch (e: Exception) {
            logger.warn("Error al obtener animal para editar (id={}): {}", idAnimal, e.message)
            ResponseEntity.status(400).body(mapOf("error" to e.message))
        }
    }

    /**
     * Actualiza los datos de un animal de una publicación propia.
     * Solo el dueño puede editar.
     *
     * @param token Token de sesión del usuario.
     * @param idAnimal ID del animal a editar.
     * @param request [AnimalUpdateRequest] con los campos a actualizar.
     * @return 200 con mensaje e id, 401 si no hay token, 400 si hay error.
     */
    @PutMapping("/{idAnimal}")
    fun editarAnimal(
        @RequestHeader("Authorization", required = false) token: String?,
        @PathVariable idAnimal: Int,
        @Valid @RequestBody request: AnimalUpdateRequest
    ): ResponseEntity<Any> {
        logger.info("PUT /api/animales/{}", idAnimal)
        if (token == null) {
            logger.warn("PUT /api/animales/{} sin token", idAnimal)
            return ResponseEntity.status(401).build()
        }
        return try {
            val animal = animalService.editarAnimal(token, idAnimal, request)
            ResponseEntity.ok(mapOf("mensaje" to "Animal actualizado", "idAnimal" to animal.idAnimal))
        } catch (e: Exception) {
            logger.warn("Error al editar animal (id={}): {}", idAnimal, e.message)
            ResponseEntity.status(400).body(mapOf("error" to e.message))
        }
    }

    /**
     * Realiza el borrado lógico de una publicación cambiando su estado a "Borrada".
     * Solo el dueño puede realizar esta acción.
     *
     * @param token Token de sesión del usuario.
     * @param idPublicacion ID de la publicación a eliminar.
     * @return 200 con mensaje de éxito, 401 si no hay token, 400 si hay error.
     */
    @DeleteMapping("/{idPublicacion}")
    fun eliminarPublicacion(
        @RequestHeader("Authorization", required = false) token: String?,
        @PathVariable idPublicacion: Int
    ): ResponseEntity<Any> {
        logger.info("DELETE /api/animales/{}", idPublicacion)
        if (token == null) {
            logger.warn("DELETE /api/animales/{} sin token", idPublicacion)
            return ResponseEntity.status(401).build()
        }
        return try {
            animalService.cambiarEstadoPublicacion(token, idPublicacion, "Borrada")
            ResponseEntity.ok(mapOf("mensaje" to "Publicación eliminada"))
        } catch (e: Exception) {
            logger.warn("Error al eliminar publicación (id={}): {}", idPublicacion, e.message)
            ResponseEntity.status(400).body(mapOf("error" to e.message))
        }
    }
}