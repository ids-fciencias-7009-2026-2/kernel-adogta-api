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
}