package com.kernel.crew.sys.adogta.servicies

import com.kernel.crew.sys.adogta.dto.request.LoginRequest
import com.kernel.crew.sys.adogta.entities.AdministradorEntity
import com.kernel.crew.sys.adogta.repositories.AdministradorRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.security.MessageDigest
import java.time.LocalDateTime
import java.util.UUID

/**
 * Servicio que contiene la lógica de negocio relacionada con la entidad Administrador.
 *
 * Se encarga de la autenticación y la validación de sesiones de administradores.
 * La gestión de reportes y baneos se encuentra en [ModeracionService].
 */
@Service
class AdministradorService(
    private val administradorRepository: AdministradorRepository
) {

    private val logger = LoggerFactory.getLogger(AdministradorService::class.java)

    /** Duración de la sesión deslizante en horas. */
    private val SESSION_DURATION_HOURS = 2L

    /**
     * Autentíca a un administrador mediante email y contraseña.
     *
     * Busca al administrador por email, compara el hash SHA-256 de la contraseña
     * y genera un token de sesión con expiración deslizante.
     *
     * @param request Credenciales de acceso (email y contraseña).
     * @return Token de sesión generado, o null si las credenciales son incorrectas.
     */
    fun login(request: LoginRequest): String? {
        logger.info("Intento de login de administrador: ${request.email}")

        val admin = administradorRepository.findByEmail(request.email) ?: return null

        val passwordHash = hashPassword(request.password)
        if (passwordHash != admin.contrasena) return null

        val token = UUID.randomUUID().toString()
        val expiracion = LocalDateTime.now().plusHours(SESSION_DURATION_HOURS)

        admin.tokenSesion = token
        admin.fechaExpiracionSesion = expiracion
        administradorRepository.save(admin)

        logger.info("Login de administrador exitoso: ${request.email}")
        return token
    }

    /**
     * Valída que el token de administrador exista y no haya expirado.
     * Si es válido, renueva la fecha de expiración deslizante.
     *
     * @param token Token de sesión a validar.
     * @return [AdministradorEntity] actualizada si el token es válido, null en caso contrario.
     */
    fun validarToken(token: String): AdministradorEntity? {
        val admin = administradorRepository.findByTokenSesion(token) ?: return null
        val expiracion = admin.fechaExpiracionSesion ?: return null

        if (LocalDateTime.now().isAfter(expiracion)) {
            logger.warn("Token de administrador expirado")
            return null
        }

        // Renovar sesión
        admin.fechaExpiracionSesion = LocalDateTime.now().plusHours(SESSION_DURATION_HOURS)
        return administradorRepository.save(admin)
    }

    /**
     * Cierra la sesión del administrador eliminando su token y fecha de expiración.
     *
     * @param token Token de sesión enviado en el header Authorization.
     */
    fun logout(token: String) {
        logger.info("Logout de administrador para token: $token")

        val admin = administradorRepository.findByTokenSesion(token) ?: return

        admin.tokenSesion = null
        admin.fechaExpiracionSesion = null
        administradorRepository.save(admin)
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