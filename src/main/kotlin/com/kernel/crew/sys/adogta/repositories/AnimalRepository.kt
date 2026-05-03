package com.kernel.crew.sys.adogta.repositories

import com.kernel.crew.sys.adogta.entities.AnimalEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AnimalRepository : JpaRepository<AnimalEntity, Long> {
}