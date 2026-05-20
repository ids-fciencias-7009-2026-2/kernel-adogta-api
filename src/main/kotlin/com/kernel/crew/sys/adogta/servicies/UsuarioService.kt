package com.kernel.crew.sys.adogta.servicies

import com.kernel.crew.sys.adogta.dto.request.LoginRequest
import com.kernel.crew.sys.adogta.dto.request.RegisterRequest
import com.kernel.crew.sys.adogta.dto.request.UpdateUsuarioRequest
import com.kernel.crew.sys.adogta.dto.response.UsuarioResponse
import com.kernel.crew.sys.adogta.entities.UsuarioEntity
import com.kernel.crew.sys.adogta.extensions.toDomain
import com.kernel.crew.sys.adogta.extensions.toEntity
import com.kernel.crew.sys.adogta.extensions.toResponse
import com.kernel.crew.sys.adogta.repositories.UsuarioRepository
import com.kernel.crew.sys.adogta.dto.request.ForgotPasswordRequest
import com.kernel.crew.sys.adogta.dto.request.ResetPasswordRequest
import com.kernel.crew.sys.adogta.dto.response.MessageResponse
import com.kernel.crew.sys.adogta.repositories.AdministradorRepository
import com.kernel.crew.sys.adogta.repositories.BanRepository
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

    @Autowired
    lateinit var emailService: EmailService

    @Autowired
    lateinit var administradorRepository: AdministradorRepository

    @Autowired
    lateinit var banRepository: BanRepository

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
    fun register(request: RegisterRequest): UsuarioResponse? {
        logger.info("Registrando nuevo usuario: ${request.email}")

        if (usuarioRepository.findByEmail(request.email) != null) {
            logger.warn("Intento de registro con correo ya existente: ${request.email}")
            return null
        }

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

        // Si está baneado.
        if (banRepository.existsByUsuarioId(usuario.id!!)) {
            logger.warn("Intento de login de usuario baneado: ${request.email}")
            return null
        }

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
     * Retorna el usuario autenticado correspondiente al token recibido como una entidad.
     *
     * Valída que el token exista y que la sesión no haya expirado.
     * Si la sesión es válida, renueva la fecha de expiración deslizante.
     *
     * @param token Token de sesión enviado en el header Authorization.
     * @return [UsuarioEntity] del usuario autenticado, o null si el token es inválido o expiró.
     */

    fun getAsEntity(token: String): UsuarioEntity? {
        val usuario = validarYRenovarSesion(token) ?: return null
        return usuario
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
     * Inicia el proceso de recuperación de contraseña para un usuario dado su correo electrónico.
     *
     * Genera un token (UUIDv4), lo almacena en la base de datos con una expiración de 1 hora
     * y envía un correo electrónico al usuario con un enlace que incluye el token.
     *
     * Siempre responde con un mensaje genérico, independientemente de si el correo existe en la plataforma.
     *
     * @param request DTO que contiene el correo electrónico del usuario que solicita la recuperación.
     * @return [MessageResponse] con un mensaje para el cliente.
     */
    fun solicitarRecuperacion(request: ForgotPasswordRequest): MessageResponse {
        logger.info("Solicitud de recuperación de contraseña para: ${request.email}")

        val usuario = usuarioRepository.findByEmail(request.email)

        if (usuario == null) {
            logger.info("No se encontró usuario con el correo ${request.email}, respondiendo mensaje genérico")
            return MessageResponse(
                "Si el correo está registrado, recibirás un enlace para restablecer tu contraseña."
            )
        }

        val token = UUID.randomUUID().toString()
        val expiracion = LocalDateTime.now().plusHours(1)

        val actualizado = usuario.copy(
            tokenRecuperacionContrasena = token,
            fechaExpiracionTokenRecuperacion = expiracion
        )
        usuarioRepository.save(actualizado)

        emailService.enviarCorreoRecuperacion(usuario.email, token)
        logger.info("Token de recuperación generado y correo enviado a ${usuario.email}")

        return MessageResponse(
            "Si el correo está registrado, recibirás un enlace para restablecer tu contraseña."
        )
    }

    /**
     * Restablece la contraseña de un usuario utilizando un token de recuperación válido.
     *
     * Valida que el token exista en la base de datos y que no haya expirado. Si el token es válido,
     * hashea la nueva contraseña, la almacena, limpia los campos relacionados con la recuperación 
     * e invalida cualquier sesión activa del usuario.
     *
     * @param request DTO que contiene el token de recuperación y la nueva contraseña en texto plano.
     * @return [MessageResponse] con la confirmación del cambio exitoso.
     * @throws IllegalArgumentException si el token es inválido.
     */
    fun restablecerContrasena(request: ResetPasswordRequest): MessageResponse {
        logger.info("Intento de restablecimiento de contraseña con token proporcionado")

        val usuario = usuarioRepository.findByTokenRecuperacionContrasena(request.token)
            ?: throw IllegalArgumentException("Token inválido o ya utilizado.")

        // Verificar que el token no haya expirado
        val expiracion = usuario.fechaExpiracionTokenRecuperacion
        if (expiracion == null || LocalDateTime.now().isAfter(expiracion)) {
            logger.warn("Token de recuperación expirado para el usuario ${usuario.email}")
            throw IllegalArgumentException("El token ha expirado. Solicita uno nuevo.")
        }

        // Hashear la nueva contraseña.
        val nuevoHash = hashPassword(request.newPassword)

        // Actualizar usuario: nueva contraseña, limpiar token de recuperación e invalidar sesión activa
        val actualizado = usuario.copy(
            contrasena = nuevoHash,
            tokenRecuperacionContrasena = null,
            fechaExpiracionTokenRecuperacion = null,
            tokenSesion = null,
            fechaExpiracionSesion = null
        )
        usuarioRepository.save(actualizado)

        logger.info("Contraseña restablecida exitosamente para el usuario ${usuario.email}")
        return MessageResponse("Contraseña restablecida exitosamente. Inicia sesión con tu nueva contraseña.")
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

    /**
     * Verifica si el usuario es administrador.
     * @param token Token de sesión del usuario.
     * @return si el usuario tiene privilegios de administrador.
     */
    fun esAdministrador(token: String): Boolean {
        val usuario = getAsEntity(token) ?: return false
        return administradorRepository.esAdministrador(usuario.email)
    }
}