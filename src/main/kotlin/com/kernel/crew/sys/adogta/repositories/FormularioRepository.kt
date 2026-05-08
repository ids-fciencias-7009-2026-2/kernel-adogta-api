package com.kernel.crew.sys.adogta.repositories

import com.kernel.crew.sys.adogta.entities.FormularioEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate

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

    /**
     * Consulta a la base de datos para obtener la fecha en la que el
     * usuario envió su último formulario
     */
    @Query("SELECT fecha_envio FROM formulario WHERE id_usuario = :id_usuario",
        nativeQuery = true)
    fun getFechaEnvioFormulario(@Param("id_usuario") id_usuario: Long): LocalDate?
}