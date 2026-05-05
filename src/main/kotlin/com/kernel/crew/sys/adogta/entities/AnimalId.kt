package com.kernel.crew.sys.adogta.entities

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class AnimalId(
    @Column(name = "id_animal")
    val idAnimal: Int = 0,

    @Column(name = "id_publicacion")
    val idPublicacion: Int = 0,

    @Column(name = "id_usuario")
    val idUsuario: Int = 0
) : Serializable