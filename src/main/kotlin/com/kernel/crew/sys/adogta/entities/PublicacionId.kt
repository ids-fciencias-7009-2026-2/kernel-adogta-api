package com.kernel.crew.sys.adogta.entities

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.SequenceGenerator
import java.io.Serializable

@Embeddable
data class PublicacionId(
    @Column(name = "id_publicacion")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "publicacion_seq")
    @SequenceGenerator(
        name = "publicacion_seq",
        sequenceName = "publicacion_id_publicacion_seq",
        allocationSize = 1
    )
    val idPublicacion: Int = 0,

    @Column(name = "id_usuario")
    val idUsuario: Int = 0
) : Serializable
