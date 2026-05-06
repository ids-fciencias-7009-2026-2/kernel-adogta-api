package com.kernel.crew.sys.adogta.entities

import jakarta.persistence.*

@Entity
@Table(name = "publicacion")
class PublicacionEntity(

    @EmbeddedId
    val id: PublicacionId? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
    val usuario: UsuarioEntity,

    @Column(length = 50)
    var estado: String = "Activa"
)
