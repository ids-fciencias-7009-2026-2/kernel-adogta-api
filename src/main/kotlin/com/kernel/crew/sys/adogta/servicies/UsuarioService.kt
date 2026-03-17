package com.kernel.crew.sys.adogta.services

import com.kernel.crew.sys.adogta.dto.request.LoginRequest
import com.kernel.crew.sys.adogta.dto.request.RegisterRequest
import com.kernel.crew.sys.adogta.dto.request.UpdateUsuarioRequest
import com.kernel.crew.sys.adogta.dto.response.UsuarioResponse
import com.kernel.crew.sys.adogta.entities.UsuarioEntity
import com.kernel.crew.sys.adogta.extensions.toDomain
import com.kernel.crew.sys.adogta.extensions.toEntity
import com.kernel.crew.sys.adogta.extensions.toResponse
import com.kernel.crew.sys.adogta.repositories.UsuarioRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.security.MessageDigest
import java.time.LocalDateTime
import java.util.UUID

/**
 * Servicio que contiene la lógica de negocio relacionada con la entidad Usuario.
 *
 * Se encarga de registro, autenticación, cierre de sesión,
 * consulta del usuario autenticado y actualización de perfil.
 *
 * La validación del token y la renovación de la sesión deslizante
 * se gestionan aquí para mantener el controlador limpio.
 */
@Service
class UsuarioService {

    private val logger = LoggerFactory.getLogger(UsuarioService::class.java)

    @Autowired
    lateinit var usuarioRepository: UsuarioRepository

    /**
     * Duración de la sesión deslizante en horas.
     * Cada petición exitosa renueva la sesión por este periodo.
     */
    private val SESSION_DURATION_HOURS = 2L

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * Hashea la contraseña antes de persistir y asigna valores
     * por defecto correspondientes a un usuario recién registrado.
     *
     * @param request Datos del nuevo usuario a registrar.
     * @return [UsuarioResponse] del usuario recién creado.
     */
    fun register(request: RegisterRequest): UsuarioResponse {
        logger.info("Registrando nuevo usuario: ${request.email}")
        val entity = request.toEntity()
        val saved = usuarioRepository.save(entity)
        return saved.toDomain().toResponse()
    }

    /**
     * Autentíca a un usuario mediante email y contraseña.
     *
     * Busca al usuario por email y compara el hash SHA-256 de la contraseña
     * recibida contra el almacenado en BD. Si las credenciales son válidas,
     * genera un token UUID y establece la fecha de expiración deslizante.
     *
     * @param request Credenciales de acceso (email y contraseña).
     * @return Token de sesión generado, o null si las credenciales son incorrectas.
     */
    fun login(request: LoginRequest): String? {
        logger.info("Intento de login: ${request.email}")

        val usuario = usuarioRepository.findByEmail(request.email) ?: return null

        val passwordHash = hashPassword(request.password)
        if (passwordHash != usuario.contrasena) return null

        val token = UUID.randomUUID().toString()
        val expiracion = LocalDateTime.now().plusHours(SESSION_DURATION_HOURS)

        val actualizado = usuario.copy(
            tokenSesion = token,
            fechaExpiracionSesion = expiracion
        )
        usuarioRepository.save(actualizado)

        logger.info("Login exitoso para ${request.email}, token generado: $token")
        return token
    }

    /**
     * Cierra la sesión del usuario eliminando su token y fecha de expiración de la BD.
     *
     * @param token Token de sesión enviado en el header Authorization.
     */
    fun logout(token: String) {
        logger.info("Logout para token: $token")

        val usuario = usuarioRepository.findByTokenSesion(token) ?: return

        val actualizado = usuario.copy(
            tokenSesion = null,
            fechaExpiracionSesion = null
        )
        usuarioRepository.save(actualizado)
    }

    /**
     * Retorna el usuario autenticado correspondiente al token recibido.
     *
     * Valída que el token exista y que la sesión no haya expirado.
     * Si la sesión es válida, renueva la fecha de expiración deslizante.
     *
     * @param token Token de sesión enviado en el header Authorization.
     * @return [UsuarioResponse] del usuario autenticado, o null si el token es inválido o expiró.
     */
    fun getMe(token: String): UsuarioResponse? {
        val usuario = validarYRenovarSesion(token) ?: return null
        return usuario.toDomain().toResponse()
    }

    /**
     * Actualiza los datos del perfil del usuario autenticado.
     *
     * Valída el token antes de aplicar los cambios. Si la sesión es válida,
     * renueva la fecha de expiración deslizante junto con la actualización.
     *
     * @param token Token de sesión enviado en el header Authorization.
     * @param request Campos del perfil a actualizar.
     * @return [UsuarioResponse] actualizado, o null si el token es inválido o expiró.
     */
    fun update(token: String, request: UpdateUsuarioRequest): UsuarioResponse? {
        logger.info("Actualizando perfil para token: $token")

        val usuario = validarYRenovarSesion(token) ?: return null

        val actualizado = usuario.copy(
            nombres = request.nombres,
            apellidoPaterno = request.apellidoPaterno,
            apellidoMaterno = request.apellidoMaterno,
            email = request.email,
            telefono = request.telefono,
            codigoPostal = request.codigoPostal
        )
        val saved = usuarioRepository.save(actualizado)
        return saved.toDomain().toResponse()
    }

    /**
     * Valída que el token exista en la BD y que la sesión no haya expirado.
     * Si es válido, renueva la fecha de expiración deslizante.
     *
     * @param token Token de sesión a validar.
     * @return [UsuarioEntity] actualizado si el token es válido, null si no lo es.
     */
    private fun validarYRenovarSesion(token: String): UsuarioEntity? {
        val usuario = usuarioRepository.findByTokenSesion(token) ?: return null

        val expiracion = usuario.fechaExpiracionSesion ?: return null

        if (LocalDateTime.now().isAfter(expiracion)) {
            logger.warn("Token expirado para usuario: ${usuario.email}")
            return null
        }

        val renovado = usuario.copy(
            fechaExpiracionSesion = LocalDateTime.now().plusHours(SESSION_DURATION_HOURS)
        )
        return usuarioRepository.save(renovado)
    }

    /**
     * Genera el hash SHA-256 de una contraseña en texto plano.
     *
     * @param password Contraseña en texto plano.
     * @return Representación hexadecimal del hash SHA-256.
     */
    private fun hashPassword(password: String): String {
        val bytes = MessageDigest
            .getInstance("SHA-256")
            .digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}