package com.kernel.crew.sys.adogta.controllers

import com.kernel.crew.sys.adogta.dto.request.RazaCreateRequest
import com.kernel.crew.sys.adogta.dto.response.RazaResponse
import com.kernel.crew.sys.adogta.repositories.RazaRepository
import com.kernel.crew.sys.adogta.servicies.RazaService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Controlador REST que expone los endpoints relacionados con la entidad Raza.
 *
 * Maneja la consulta de razas disponibles en el sistema bajo la ruta base '/api/razas'.
 *
 * El acceso a datos está delegado a [RazaRepository].
 * Este controlador solo recibe peticiones y devuelve respuestas.
 */
@RestController
@RequestMapping("/api/razas")
class RazaController {

    private val logger = LoggerFactory.getLogger(RazaController::class.java)

    @Autowired
    lateinit var razaRepository: RazaRepository

    @Autowired
    lateinit var razaSyncService: RazaService

    /**
     * Retorna la lista completa de razas registradas en el sistema.
     *
     * @return 200 con la lista de [RazaResponse].
     */
    @GetMapping
    fun listarRazas(): ResponseEntity<List<RazaResponse>> {
        logger.info("GET /api/razas")

        val razas = razaRepository.findAll().map(RazaResponse::from)
        return ResponseEntity.ok(razas)
    }

    /**
     * Retorna la información de una raza específica por su ID.
     *
     * @param id Identificador numérico de la raza.
     * @return 200 con el [RazaResponse] correspondiente,
     *         o 404 si no existe una raza con ese ID.
     */
    @GetMapping("/{id}")
    fun obtenerRaza(@PathVariable id: Int): ResponseEntity<Any> {
        logger.info("GET /api/razas/$id")

        val raza = razaRepository.findById(id).orElse(null)
            ?: return ResponseEntity.status(404).body(mapOf("error" to "Raza no encontrada con id: $id"))

        return ResponseEntity.ok(RazaResponse.from(raza))
    }

    /**
     * Agrega una raza buscando en The Dog API / The Cat API por nombre exacto.
     *
     * @param request Datos de la raza a agregar (nombre y tipo).
     * @return 200 con la raza creada o existente, 404 si no se encontró coincidencia.
     */
    @PostMapping
    fun agregarRaza(@RequestBody request: RazaCreateRequest): ResponseEntity<RazaResponse> {
        logger.info("POST /api/razas - tipo={} nombre={}", request.tipo, request.nombre)

        val respuesta = razaSyncService.crearRaza(request)
        return if (respuesta.mensaje != null) {
            ResponseEntity.status(404).body(respuesta)
        } else {
            ResponseEntity.ok(respuesta)
        }
    }
}