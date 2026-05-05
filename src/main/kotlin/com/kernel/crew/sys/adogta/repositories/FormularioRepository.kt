package com.kernel.crew.sys.adogta.repositories

import com.kernel.crew.sys.adogta.entities.FormularioEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Repositorio JPA para la entidad [FormularioEntity].
 *
 * Provee operaciones CRUD básicas heredadas de [JpaRepository]
 */

@Repository
interface FormularioRepository : JpaRepository<FormularioEntity, Long> {
    /**
     * Busca los formularios por el ID del usuario al que pertenecen.
     * Se usa para obtener el historial de formularios de un usuario específico.
     */
    fun findAllByUsuarioId(idUsuario: Long): List<FormularioEntity>
}