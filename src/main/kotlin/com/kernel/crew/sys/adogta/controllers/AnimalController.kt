package com.kernel.crew.sys.adogta.controllers

import com.kernel.crew.sys.adogta.dto.request.AnimalRequest
import com.kernel.crew.sys.adogta.servicies.AnimalService
import com.kernel.crew.sys.adogta.dto.request.AnimalUpdateRequest
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping

/**
 * Controlador REST que expone los endpoints relacionados con la entidad Animal.
 *
 * Maneja las operaciones de publicación y consulta de animales en adopción
 * bajo la ruta base '/api/animales'.
 *
 * Toda la lógica de negocio está delegada a [AnimalService].
 * Este controlador solo recibe peticiones, llama al service y devuelve respuestas.
 */
@RestController
@RequestMapping("/api/animales")
class AnimalController {

    private val logger = LoggerFactory.getLogger(AnimalController::class.java)

    @Autowired
    lateinit var animalService: AnimalService

    /**
     * Retorna la lista de publicaciones activas con sus animales.
     *
     * @return 200 con la lista de animales en adopción.
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
     * Publica un nuevo animal en adopción asociado al usuario autenticado.
     *
     * Endpoint protegido: requiere un token válido en el header 'Authorization'.
     *
     * @param token Token de sesión enviado en el header 'Authorization'.
     * @param request Datos del animal a publicar.
     * @return 201 con los datos del animal publicado,
     *         400 si los datos son inválidos,
     *         o 401 si el token es nulo, inválido o la sesión expiró.
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
        if (token == null) return ResponseEntity.status(401).build()
        return try {
            ResponseEntity.ok(animalService.listarMisPublicaciones(token))
        } catch (e: Exception) {
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
        if (token == null) return ResponseEntity.status(401).build()
        return try {
            ResponseEntity.ok(animalService.obtenerAnimalParaEditar(token, idAnimal))
        } catch (e: Exception) {
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
        if (token == null) return ResponseEntity.status(401).build()
        return try {
            val animal = animalService.editarAnimal(token, idAnimal, request)
            ResponseEntity.ok(mapOf("mensaje" to "Animal actualizado", "idAnimal" to animal.idAnimal))
        } catch (e: Exception) {
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
        if (token == null) return ResponseEntity.status(401).build()
        return try {
            animalService.cambiarEstadoPublicacion(token, idPublicacion, "Borrada")
            ResponseEntity.ok(mapOf("mensaje" to "Publicación eliminada"))
        } catch (e: Exception) {
            ResponseEntity.status(400).body(mapOf("error" to e.message))
        }
    }
}
