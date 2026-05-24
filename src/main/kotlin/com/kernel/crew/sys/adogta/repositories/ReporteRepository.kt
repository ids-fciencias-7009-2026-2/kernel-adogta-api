package com.kernel.crew.sys.adogta.repositories

import com.kernel.crew.sys.adogta.entities.ReporteEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

/**
 * Repositorio JPA para la entidad [ReporteEntity].
 */
@Repository
interface ReporteRepository : JpaRepository<ReporteEntity, Long> {

    /** Lista los reportes filtrados por estado. */
    fun findByEstado(estado: String): List<ReporteEntity>

    /** Cuenta los reportes a una publicacion mediante los parámetros idPublicacion y estado*/
    fun countByIdPublicacionAndEstado(idPublicacion: Int, estado: String): Int

    /**
     * Verifica si un usuario ya reportó una publicación específica.
     * @param idUsuario ID del usuario que reporta.
     * @param idPublicacion ID de la publicación.
     * @param idUsuarioPublicacion ID del dueño de la publicación.
     */
    @Query("SELECT COUNT(r) > 0 FROM ReporteEntity r WHERE r.idUsuario = :idUsuario AND r.idPublicacion = :idPublicacion")
    fun existsByUsuarioIdAndPublicacionId(
        @Param("idUsuario") idUsuario: Long,
        @Param("idPublicacion") idPublicacion: Int
    ): Boolean

    /**
     * Devuelve los reportes de publicaciones que están en estado "En revision".
     * Usa un join implícito aprovechando la FK compuesta.
     */
    @Query("SELECT r FROM ReporteEntity r, PublicacionEntity p " +
        "WHERE r.idPublicacion = p.idPublicacion " +
        "AND r.idUsuarioPublicacion = p.idUsuario " +
        "AND p.estado = 'En revision'")
    fun findReportesDePublicacionesEnRevision(): List<ReporteEntity>

}