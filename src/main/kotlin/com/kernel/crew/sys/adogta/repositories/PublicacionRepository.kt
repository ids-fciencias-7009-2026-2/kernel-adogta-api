package com.kernel.crew.sys.adogta.repositories

import com.kernel.crew.sys.adogta.entities.PublicacionEntity
import com.kernel.crew.sys.adogta.entities.PublicacionId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PublicacionRepository : JpaRepository<PublicacionEntity, PublicacionId>
