package com.kernel.crew.sys.adogta.controllers

import com.kernel.crew.sys.adogta.domain.Usuario
import com.kernel.crew.sys.adogta.dto.request.LoginRequest
import com.kernel.crew.sys.adogta.dto.request.RegisterRequest
import com.kernel.crew.sys.adogta.dto.request.UpdateUsuarioRequest
import com.kernel.crew.sys.adogta.dto.response.UsuarioResponse
import com.kernel.crew.sys.adogta.extensions.toDomain
import com.kernel.crew.sys.adogta.extensions.toResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/usuarios")
class UsuarioController {

    private val logger = LoggerFactory.getLogger(UsuarioController::class.java)

    private val usuarioFake = Usuario(
        id = 1L,
        nombre = "admin",
        email = "admin@adogta.com",
        codigoPostal = "06600",
        telefono = "5512345678",
        password = "1234",
        googleId = null,
        authProvider = "local",
        rol = "admin",
        emailVerificado = true,
        isBaned = false,
        banMotive = null,
        banDate = null,
        bannedBy = null,
        reputation = 100,
        aceptaTerminos = true,
        fechaAceptaTerminos = "2026-01-01",
        fechaRegistro = "2026-01-01",
        ultimoAcceso = "2026-02-23",
        fechaUpdate = "2026-02-23"
    )

    @GetMapping("/me")
    fun getMe(): ResponseEntity<UsuarioResponse> {
        logger.info("GET /usuarios/me")
        return ResponseEntity.ok(usuarioFake.toResponse())
    }

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<UsuarioResponse> {
        logger.info("POST /usuarios/register - ${request.email}")
        val nuevoUsuario = request.toDomain()
        return ResponseEntity.status(201).body(nuevoUsuario.toResponse())
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<Any> {
        logger.info("POST /usuarios/login - ${request.email}")
        return if (request.email == usuarioFake.email && request.password == usuarioFake.password) {
            ResponseEntity.ok(mapOf("mensaje" to "Login exitoso", "token" to "fake-token-123"))
        } else {
            logger.warn("Login fallido para ${request.email}")
            ResponseEntity.status(401).body(mapOf("error" to "Credenciales incorrectas"))
        }
    }

    @PostMapping("/logout")
    fun logout(): ResponseEntity<Any> {
        logger.info("POST /usuarios/logout")
        return ResponseEntity.ok(mapOf("mensaje" to "Sesión cerrada correctamente"))
    }

    @PutMapping
    fun update(@RequestBody request: UpdateUsuarioRequest): ResponseEntity<UsuarioResponse> {
        logger.info("PUT /usuarios - ${request.email}")
        val usuarioActualizado = usuarioFake.copy(
            nombre = request.nombre,
            email = request.email,
            telefono = request.telefono,
            codigoPostal = request.codigoPostal
        )
        return ResponseEntity.ok(usuarioActualizado.toResponse())
    }
}