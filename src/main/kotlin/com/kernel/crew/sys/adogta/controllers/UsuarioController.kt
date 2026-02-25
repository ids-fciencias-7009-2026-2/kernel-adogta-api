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

/**
 * Controlador REST que expone los endpoints relacionaos con la entidad [Usuario].
 *
 * Manja las operaciones de registro, autenticación, consulta y actualización
 * de usuarios bajo la ruta base '/usuarios'.
 *
 * La utenticación es simulada en memoria. No se utiliza base de datos
 * ni un proveedor de identidad real en esta versión.
 */
@RestController
@RequestMapping("/usuarios")
class UsuarioController {

    private val logger = LoggerFactory.getLogger(UsuarioController::class.java)

    /**
     * Conjunto de tokens activos generador tras un login exitoso.
     * Se utiliza para validar el acceso a endpoints protegidos.
     */
    private val activeTokens = mutableSetOf<String>()

    /**
     * Usuario simulado utilizado como fuente de datos en ausencia de base de datos.
     * Representa al administrador del sistema con credenciales de prueba.
     */
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

    /**
     * Retorna la información del usuario autenticado actualmente .
     *
     * Endpoint protegido: requiere un token válido en el header 'Authorization'.
     *
     * @param token Token de sesión enviado en el header 'Authorization'. Opcional en firma,
     *              pero obligatorio para obtener respuesta exitosa.
     * @return 200 con el usuario [UsuarioResponse] del usuario autenticado,
     *         o 401 si l token es nulo o no corresponde a una sesión activa.
     */
    @GetMapping("/me")
    fun getMe(@RequestHeader("Authorization", required = false) token: String?): ResponseEntity<UsuarioResponse> {
        logger.info("GET /usuarios/me")
        if (token == null || !activeTokens.contains(token)) {
            return ResponseEntity.status(401).build()
        }
        return ResponseEntity.ok(usuarioFake.toResponse())
    }

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * Convierte el [RegisterRequest] recibido en un objeto de dominio [Usuario]
     * mediante la extensión fucntion [toDomain], y retorna su representación
     * pública como [UsuarioResponse].
     *
     * En esta versión simulada no se persiste el usuario en base de datos.
     *
     * @param request Datos dle nuvo usuario a registrar.
     * @return 201 con el [UsuarioResponse] del usuario recién creado.
     */
    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<UsuarioResponse> {
        logger.info("POST /usuarios/register - ${request.email}")
        val nuevoUsuario = request.toDomain()
        return ResponseEntity.status(201).body(nuevoUsuario.toResponse())
    }

    /**
     * Autentica a un usuario mediante email y contraseña.
     *
     * Compara las credenciales recibidas contra el usuario simulado [usuarioFake],
     * aplicando hash SHA-256 a ambas contraseñas antes de compararlas.
     * Si las credenciales son válidas, genera un token UUID y lo registra
     * en [activeTokens].
     *
     * @param request Credenciales de acceso del usuario (email y password).
     * @return 200 con un mapa que contiene el token de sesión generado,
     *         o 401 con un mensaje de error si las credenciales son incorrectas.
     */
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

    /**
     * Cierra la sesión del usuario eliminando su token activo.
     *
     * El token recibido se remueve de [activeTokens], invalidando cualquier
     * solicitud futura que lo utilice.
     *
     * @param token Token de sesión enviado en el header 'Authorization'.
     * @return 200 con un mensaje de confirmación de cierre de sesión.
     */
    @PostMapping("/logout")
    fun logout(@RequestHeader("Authorization") token: String): ResponseEntity<Any> {
        logger.info("POST /usuarios/logout - token: $token")

        activeTokens.remove(token)
        logger.info("Token eliminado: $token")

        return ResponseEntity.ok(mapOf("mensaje" to "Sesión cerrada correctamente"))
    }

    /**
     * Actualiza los datos del perfil del usuario autenticado.
     *
     * Endpoint protegido: requiere un token válido en el header 'Authorization'.
     * Aplica los cambios recibidos sobre [usuarioFake] mediante 'copy'.
     * retornando la versión actualizada como [UsuarioResponse].
     *
     * Los cambios no se peristen en base de datos en esta versión simulada.
     *
     * @param token Token de sesión enviado en el header 'Authorization'. Opcional en el firma,
     *              pero obligatorio para obtener respuesta exitosa.
     * @param request Campos del perfil a actualizar.
     * @return 200 con el [UsuarioResponse] actualizado,
     *         o 401 si el token ees nulo o no corresponde a una sesión activa.
     */
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

    /**
     * Genera el hash SHA-256 de una contraseña en texto plano.
     *
     * COnvierte el resultado en una cadena hexdecimal de 64 caracteres.
     *
     * @param password Contraseña en texto plano a hashear.
     * @return Representación hexadecimal del hash SHA-256.
     */
    private fun hashPassword(password: String): String {
        val bytes = MessageDigest
            .getInstance("SHA-256")
            .digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    /**
     * Genera un token de sesión único basado en UUID v4.
     *
     * @return Cadena UUID aleatoria en formato estándar (xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx).
     */
    private fun tokenGenerator(): String {
        return UUID.randomUUID().toString()
    }

}