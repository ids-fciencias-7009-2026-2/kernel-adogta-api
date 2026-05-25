package com.kernel.crew.sys.adogta.repositories

import com.kernel.crew.sys.adogta.entities.BanEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

/**
 * Repositorio JPA para [BanEntity].
 */
@Repository
interface BanRepository : JpaRepository<BanEntity, Long> {

    /**
     * Verifica si un usuario ya está baneado.
     *
     * @param idUsuario ID del usuario.
     * @return si hay un registro de baneo del usuario.
     */
    @Query("SELECT COUNT(b) > 0 FROM BanEntity b WHERE b.usuario.id = :idUsuario")
    fun existsByUsuarioId(@Param("idUsuario") idUsuario: Long): Boolean

    /**
     * Encuentra el motivo por el que un usuario fue baneado.
     * 
     * @param idUsuario ID del usuario baneado.
     * @return motivo del baneo.
     */
    @Query("SELECT b.motivo FROM BanEntity b WHERE b.usuario.id = :idUsuario ORDER BY b.fecha DESC LIMIT 1")
    fun findMotivoBanByUsuarioId(@Param("idUsuario") idUsuario: Long): String?
}