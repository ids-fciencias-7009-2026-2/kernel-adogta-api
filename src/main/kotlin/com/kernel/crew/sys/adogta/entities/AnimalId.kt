package com.kernel.crew.sys.adogta.entities

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.SequenceGenerator
import java.io.Serializable

@Embeddable
data class AnimalId(
    @Column(name = "id_animal")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "animal_seq")
    @SequenceGenerator(
        name = "animal_seq",
        sequenceName = "animal_id_animal_seq",
        allocationSize = 1
    )
    val idAnimal: Int = 0,

    @Column(name = "id_publicacion")
    val idPublicacion: Int = 0,

    @Column(name = "id_usuario")
    val idUsuario: Int = 0
) : Serializable
