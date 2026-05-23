package com.kernel.crew.sys.adogta.repositories

import com.kernel.crew.sys.adogta.entities.AdministradorEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

/**
 * Repositorio JPA para [AdministradorEntity].
 */
@Repository
interface AdministradorRepository : JpaRepository<AdministradorEntity, Long> {

    /** Busca un administrador por su correo electrónico. */
    fun findByEmail(email: String): AdministradorEntity?

    /** Busca un administrador por su token de sesión. */
    fun findByTokenSesion(tokenSesion: String): AdministradorEntity?

    /**
     * Verifica si un email pertenece a un administrador.
     *
     * @param email Correo electrónico a verificar.
     * @return true si el email corresponde a un administrador.
     */
    @Query("SELECT COUNT(a) > 0 FROM AdministradorEntity a WHERE a.email = :email")
    fun esAdministrador(@Param("email") email: String): Boolean
}