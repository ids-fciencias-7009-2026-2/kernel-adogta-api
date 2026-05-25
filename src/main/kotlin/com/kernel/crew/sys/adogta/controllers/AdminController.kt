package com.kernel.crew.sys.adogta.controllers

import com.kernel.crew.sys.adogta.dto.request.LoginRequest
import com.kernel.crew.sys.adogta.servicies.AdministradorService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Controlador REST que expone los endpoints relacionados con la entidad Administrador.
 *
 * Maneja las operaciones de autenticación y consulta de administradores
 * bajo la ruta '/admin'.
 *
 * La lógica de negocio está delegada a [AdministradorService].
 */
@RestController
@RequestMapping("/admin")
class AdminController(
    private val administradorService: AdministradorService
) {

    private val logger = LoggerFactory.getLogger(AdminController::class.java)

    /**
     * Autentica a un administrador mediante email y contraseña.
     *
     * @param request Credenciales de acceso (email y password).
     * @return 200 con token de sesión, 401 si las credenciales son incorrectas.
     */
    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<Any> {
        logger.info("POST /admin/login - ${request.email}")

        val token = administradorService.login(request)
            ?: return ResponseEntity.status(401).body(mapOf("error" to "Credenciales incorrectas"))

        return ResponseEntity.ok(mapOf("token" to token))
    }

    /**
     * Cierra la sesión del administrador invalidando su token.
     *
     * @param token Token de sesión enviado en el header 'Authorization'.
     * @return 200 confirmación, 400 sin token.
     */
    @PostMapping("/logout")
    fun logout(@RequestHeader("Authorization", required = false) token: String?): ResponseEntity<Any> {
        logger.info("POST /admin/logout")

        if (token == null) {
            return ResponseEntity.status(400).body(mapOf("mensaje" to "Nada que hacer"))
        }

        administradorService.logout(token)
        return ResponseEntity.ok(mapOf("mensaje" to "Sesión cerrada correctamente"))
    }

    /**
     * Retorna la información del administrador autenticado.
     *
     * @param token Token de sesión enviado en el header 'Authorization'.
     * @return 200 con los datos del administrador, 401 si el token es inválido o expiró.
     */
    @GetMapping("/me")
    fun getMe(@RequestHeader("Authorization", required = false) token: String?): ResponseEntity<Any> {
        logger.info("GET /admin/me")

        if (token == null) return ResponseEntity.status(401).build()

        val admin = administradorService.validarToken(token)
            ?: return ResponseEntity.status(401).build()

        return ResponseEntity.ok(
            mapOf(
                "id" to admin.idAdministrador,
                "email" to admin.email,
                "nombres" to admin.nombres,
                "apellidoPaterno" to admin.apellidoPaterno,
                "apellidoMaterno" to admin.apellidoMaterno
            )
        )
    }
}