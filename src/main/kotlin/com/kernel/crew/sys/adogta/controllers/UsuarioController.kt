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
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.MessageDigest
import java.util.UUID


@RestController
@RequestMapping("/usuarios")
class UsuarioController {

    private val logger = LoggerFactory.getLogger(UsuarioController::class.java)
    private val activeTokens = mutableSetOf<String>()

    private val usuarioFake = Usuario(
        id = 1L,
        nombre = "admin",
        apellidoPaterno = "howard",
        apellidoMaterno = "benson",
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
    fun getMe(@RequestHeader("Authorization", required = false) token: String?): ResponseEntity<UsuarioResponse> {
        logger.info("GET /usuarios/me")
        if (token == null || !activeTokens.contains(token)) {
            return ResponseEntity.status(401).build()
        }
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

        val passwordHash = hashPassword(request.password)
        val usuarioFakeHash = hashPassword(usuarioFake.password!!)

        return if (request.email == usuarioFake.email && passwordHash == usuarioFakeHash) {
            val token = tokenGenerator()
            activeTokens.add(token)
            logger.info("Login exitoso, token generado: $token")
            ResponseEntity.ok(mapOf("token" to token))
        } else {
            logger.warn("Login fallido para ${request.email}")
            ResponseEntity.status(401).body(mapOf("error" to "Credenciales incorrectas"))
        }
    }

    @PostMapping("/logout")
    fun logout(@RequestHeader("Authorization") token: String): ResponseEntity<Any> {
        logger.info("POST /usuarios/logout - token: $token")

        activeTokens.remove(token)
        logger.info("Token eliminado: $token")

        return ResponseEntity.ok(mapOf("mensaje" to "Sesión cerrada correctamente"))
    }

    @PutMapping
    fun update(
        @RequestHeader("Authorization", required = false) token: String?,
        @RequestBody request: UpdateUsuarioRequest
    ): ResponseEntity<Any> {
        logger.info("PUT /usuarios - ${request.email}")

        if (token == null || !activeTokens.contains(token)) {
            return ResponseEntity.status(401).build()
        }

        val usuarioActualizado = usuarioFake.copy(
            nombre = request.nombre,
            apellidoPaterno = request.apellidoPaterno,
            apellidoMaterno = request.apellidoMaterno,
            email = request.email,
            telefono = request.telefono,
            codigoPostal = request.codigoPostal
        )
        return ResponseEntity.ok(usuarioActualizado.toResponse())
    }

    private fun hashPassword(password: String): String {
        val bytes = MessageDigest
            .getInstance("SHA-256")
            .digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    private fun tokenGenerator(): String {
        return UUID.randomUUID().toString()
    }

}