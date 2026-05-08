package com.kernel.crew.sys.adogta.repositories

import com.kernel.crew.sys.adogta.entities.SolicitudEntity
import com.kernel.crew.sys.adogta.entities.SolicitudId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SolicitudRepository : JpaRepository<SolicitudEntity, SolicitudId> {
    fun existsByIdUsuarioAndIdAnimalAndIdPublicacionAnimalAndIdUsuarioAnimal(
        idUsuario: Int,
        idAnimal: Int,
        idPublicacionAnimal: Int,
        idUsuarioAnimal: Int
    ): Boolean
}