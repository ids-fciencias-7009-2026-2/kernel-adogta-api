package com.kernel.crew.sys.adogta.repositories

import com.kernel.crew.sys.adogta.entities.UsuarioEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

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
}