package com.kernel.crew.sys.adogta.controllers

import com.kernel.crew.sys.adogta.dto.request.LoginRequest
import com.kernel.crew.sys.adogta.dto.request.RegisterRequest
import com.kernel.crew.sys.adogta.dto.request.UpdateUsuarioRequest
import com.kernel.crew.sys.adogta.dto.response.UsuarioResponse
import com.kernel.crew.sys.adogta.services.UsuarioService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Controlador REST que expone los endpoints relacionados con la entidad Usuario.
 *
 * Maneja las operaciones de registro, autenticación, consulta y actualización
 * de usuarios bajo la ruta base '/usuarios'.
 *
 * Toda la lógica de negocio está delegada a [UsuarioService].
 * Este controlador solo recibe peticiones, llama al service y devuelve respuestas.
 */
@RestController
@RequestMapping("/usuarios")
class UsuarioController {

    private val logger = LoggerFactory.getLogger(UsuarioController::class.java)

    @Autowired
    lateinit var usuarioService: UsuarioService

    /**
     * Retorna la información del usuario autenticado actualmente.
     *
     * Endpoint protegido: requiere un token válido en el header 'Authorization'.
     * La validación y renovación de la sesión deslizante ocurre en el service.
     *
     * @param token Token de sesión enviado en el header 'Authorization'.
     * @return 200 con el [UsuarioResponse] del usuario autenticado,
     *         o 401 si el token es nulo, inválido o la sesión expiró.
     */
    @GetMapping("/me")
    fun getMe(@RequestHeader("Authorization", required = false) token: String?): ResponseEntity<UsuarioResponse> {
        logger.info("GET /usuarios/me")

        if (token == null) return ResponseEntity.status(401).build()

        val usuario = usuarioService.getMe(token)
            ?: return ResponseEntity.status(401).build()

        return ResponseEntity.ok(usuario)
    }

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param request Datos del nuevo usuario a registrar.
     * @return 201 con el [UsuarioResponse] del usuario recién creado.
     */
    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<UsuarioResponse> {
        logger.info("POST /usuarios/register - ${request.email}")

        val nuevoUsuario = usuarioService.register(request)
        return ResponseEntity.status(201).body(nuevoUsuario)
    }

    /**
     * Autentíca a un usuario mediante email y contraseña.
     *
     * Si las credenciales son válidas, retorna un token de sesión.
     *
     * @param request Credenciales de acceso del usuario (email y password).
     * @return 200 con un mapa que contiene el token de sesión generado,
     *         o 401 si las credenciales son incorrectas.
     */
    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<Any> {
        logger.info("POST /usuarios/login - ${request.email}")

        val token = usuarioService.login(request)
            ?: return ResponseEntity.status(401).body(mapOf("error" to "Credenciales incorrectas"))

        return ResponseEntity.ok(mapOf("token" to token))
    }

    /**
     * Cierra la sesión del usuario eliminando su token activo de la BD.
     *
     * @param token Token de sesión enviado en el header 'Authorization'.
     * @return 200 con un mensaje de confirmación de cierre de sesión.
     */
    @PostMapping("/logout")
    fun logout(@RequestHeader("Authorization") token: String): ResponseEntity<Any> {
        logger.info("POST /usuarios/logout - token: $token")

        usuarioService.logout(token)
        return ResponseEntity.ok(mapOf("mensaje" to "Sesión cerrada correctamente"))
    }

    /**
     * Actualiza los datos del perfil del usuario autenticado.
     *
     * Endpoint protegido: requiere un token válido en el header 'Authorization'.
     *
     * @param token Token de sesión enviado en el header 'Authorization'.
     * @param request Campos del perfil a actualizar.
     * @return 200 con el [UsuarioResponse] actualizado,
     *         o 401 si el token es nulo, inválido o la sesión expiró.
     */
    @PutMapping
    fun update(
        @RequestHeader("Authorization", required = false) token: String?,
        @RequestBody request: UpdateUsuarioRequest
    ): ResponseEntity<Any> {
        logger.info("PUT /usuarios - ${request.email}")

        if (token == null) return ResponseEntity.status(401).build()

        val usuarioActualizado = usuarioService.update(token, request)
            ?: return ResponseEntity.status(401).build()

        return ResponseEntity.ok(usuarioActualizado)
    }
}