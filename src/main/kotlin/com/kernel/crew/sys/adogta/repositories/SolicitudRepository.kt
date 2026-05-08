package com.kernel.crew.sys.adogta.repositories

import com.kernel.crew.sys.adogta.entities.SolicitudEntity
import com.kernel.crew.sys.adogta.entities.SolicitudId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SolicitudRepository : JpaRepository<SolicitudEntity, SolicitudId> {

    /**
     * Busca todas las solicitudes de un usuario por su ID.
     * Se usa para mostrar el historial de solicitudes del adoptante.
     */
    fun findAllByIdIdUsuario(idUsuario: Int): List<SolicitudEntity>

    /**
     * Verifica si ya existe una solicitud activa de un usuario para un animal específico.
     * Evita solicitudes duplicadas.
     */
    fun existsByIdIdUsuarioAndIdAnimalAndIdPublicacionAnimal(
        idUsuario: Int,
        idAnimal: Int,
        idPublicacionAnimal: Int
    ): Boolean
}