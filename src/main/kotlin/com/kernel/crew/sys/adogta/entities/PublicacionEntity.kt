package com.kernel.crew.sys.adogta.entities

import jakarta.persistence.*

@Entity
@Table(name = "publicacion")
@IdClass(PublicacionId::class)
class PublicacionEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "publicacion_seq")
    @SequenceGenerator(
        name = "publicacion_seq",
        sequenceName = "publicacion_id_publicacion_seq",
        allocationSize = 1
    )
    @Column(name = "id_publicacion")
    val idPublicacion: Int = 0,

    @Id
    @Column(name = "id_usuario")
    val idUsuario: Int = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
    val usuario: UsuarioEntity,

    @Column(length = 50)
    var estado: String = "Activa"
)