package com.kernel.crew.sys.adogta.repositories

import com.kernel.crew.sys.adogta.entities.RazaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RazaRepository : JpaRepository<RazaEntity, Int> {
	fun findByNombreIgnoreCaseAndTipoIgnoreCase(nombre: String, tipo: String): RazaEntity?

	fun findAllByTipoIgnoreCase(tipo: String): List<RazaEntity>
}