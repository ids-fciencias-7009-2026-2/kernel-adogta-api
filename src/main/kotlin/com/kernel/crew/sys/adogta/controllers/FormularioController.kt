package com.kernel.crew.sys.adogta.controllers

import com.kernel.crew.sys.adogta.dto.request.FormularioRequest
import com.kernel.crew.sys.adogta.dto.response.FormularioResponse
import com.kernel.crew.sys.adogta.servicies.FormularioService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.kernel.crew.sys.adogta.dto.response.FormularioStringsResponse
import java.time.LocalDate
import java.time.Period

/**
 * Controlador REST que expone los endpoints relacionados con la entidad Formulario.
 *
 * Maneja la operacion de registro 
 * de formularios bajo la ruta base '/formularios'.
 *
 * Toda la lógica de negocio está delegada a [FormularioService].
 * Este controlador solo recibe peticiones, llama al service y devuelve respuestas.
 */
 @RestController
 @RequestMapping("/formularios")
 class FormularioController {

    private val logger = LoggerFactory.getLogger(FormularioController::class.java)

    @Autowired
    lateinit var formularioService: FormularioService

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param request Respuestas del formulario.
     * @return 201 con el [FormularioResponse] del formulario recién creado.
     */
    @PostMapping("/guardar")
    fun guardarFormulario(@RequestBody request: FormularioRequest, @RequestHeader("Authorization") token: String): ResponseEntity<Any> {
        logger.info("POST /formularios/guardar")

        val formularioGuardado = formularioService.guardarFormulario(request, token)

        return ResponseEntity.status(201).body(formularioGuardado)
    }

    @GetMapping("/puede-responder")
    fun validarTiempo(@RequestHeader("Authorization") token: String): ResponseEntity<Any> {
        logger.info("GET /formularios/puede-responder")

        val ultimaFecha = formularioService.obtenerFechaEnvioFormulario(token)
            ?: return ResponseEntity.ok(mapOf("mensaje" to "Puede contestar de nuevo."))

        val fechaActual = LocalDate.now()
        val diasTranscurridos = java.time.temporal.ChronoUnit.DAYS.between(ultimaFecha, fechaActual)

        if (diasTranscurridos < 365) {
            val diasFaltantes = 365 - diasTranscurridos
            return ResponseEntity.status(409).body(
                mapOf("error" to "Debe pasar un año para que puedas contestar el cuestionario de nuevo. Faltan $diasFaltantes días.")
            )
        }

        return ResponseEntity.ok(mapOf("mensaje" to "Puede contestar de nuevo."))
    }

    /**
     * Obtiene el formulario contestado por el usuario,
     * con labels de las respuestas.
     *
     * @param token Token de sesión del usuario.
     * @return [FormularioStringsResponse], 404 si no ha contestado ninguno.
     */
    @GetMapping("/ultimo")
    fun obtenerUltimoFormularioStrings(
        @RequestHeader("Authorization") token: String
    ): ResponseEntity<Any> {
        logger.info("GET /formularios/ultimo")
        val formulario = formularioService.obtenerUltimoFormularioStrings(token)
            ?: return ResponseEntity.status(404).body(
                mapOf("error" to "No se encontró un formulario para este usuario.")
            )
        return ResponseEntity.ok(formulario)
    }

    

}