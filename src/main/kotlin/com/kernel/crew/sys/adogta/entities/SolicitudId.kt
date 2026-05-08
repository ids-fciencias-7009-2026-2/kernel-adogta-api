package com.kernel.crew.sys.adogta.entities

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.SequenceGenerator
import java.io.Serializable

@Embeddable
data class SolicitudId(
    @Column(name = "id_solicitud")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "solicitud_seq")
    @SequenceGenerator(
        name = "solicitud_seq",
        sequenceName = "solicitud_id_solicitud_seq",
        allocationSize = 1
    )
    val idSolicitud: Int = 0,

    @Column(name = "id_usuario")
    val idUsuario: Int = 0
) : Serializable
