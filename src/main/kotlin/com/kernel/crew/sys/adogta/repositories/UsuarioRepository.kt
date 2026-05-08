package com.kernel.crew.sys.adogta.repositories

import com.kernel.crew.sys.adogta.entities.UsuarioEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

/**
 * Repositorio JPA para la entidad [UsuarioEntity].
 *
 * Provee operaciones CRUD básicas heredadas de [JpaRepository],
 * más las consultas personalizadas necesarias para el flujo de autenticación.
 */
@Repository
interface UsuarioRepository : JpaRepository<UsuarioEntity, Long> {

    /**
     * Busca un usuario por su correo electrónico.
     * Se usa en el flujo de login para verificar credenciales.
     */
    fun findByEmail(email: String): UsuarioEntity?

    /**
     * Busca un usuario por su token de sesión activo.
     * Se usa en endpoints protegidos para validar el token del header Authorization.
     */
    fun findByTokenSesion(tokenSesion: String): UsuarioEntity?

    /**
     * Busca un usuario por su token de recuperación de contraseña
     * Se usa en concretamente para el caso de solicitud de recuperación.
     */
    fun findByTokenRecuperacionContrasena(token: String): UsuarioEntity?

    /**
     * Se encarga de actualizar el atributo "envio_formulario"
     * Se usa para saber si el usuario tiene que responder el cuestionario.
     */
    @Modifying
    @Transactional
    @Query("UPDATE usuario SET envio_formulario = :estado WHERE token_sesion = :token",
        nativeQuery = true)
    fun updateCuestionarioStatus(token: String, estado: Boolean): Int

}