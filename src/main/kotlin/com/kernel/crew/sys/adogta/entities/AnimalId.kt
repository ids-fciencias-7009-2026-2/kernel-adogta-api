package com.kernel.crew.sys.adogta.entities

import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class AnimalId(
    val id_animal: Int = 0,
    val id_publicacion: Int = 0,
    val id_usuario: Int = 0
) : Serializable