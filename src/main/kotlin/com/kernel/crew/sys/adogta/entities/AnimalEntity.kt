package com.kernel.crew.sys.adogta.entities

import jakarta.persistence.*

@Entity
@Table(name = "Animal")
class AnimalEntity(

    @EmbeddedId
    val id: AnimalId? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPublicacion")
    @JoinColumns(
        JoinColumn(name = "id_publicacion"),
        JoinColumn(name = "id_usuario")
    )
    val publicacion: PublicacionEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idUsuario")
    @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
    val usuario: UsuarioEntity,

    @Column(nullable = false, length = 100)
    var nombre: String,

    @Column(name = "estado_vacunacion", length = 50)
    var estadoVacunacion: String,

    var esterilizado: Boolean,

    @Column(columnDefinition = "TEXT")
    var descripcion: String,

    var entrenado: Boolean,

    @Column(name = "codigo_postal", length = 10)
    var codigoPostal: String,

    var edad: Int,

    @Column(length = 6)
    var tipo: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_raza")
    var raza: RazaEntity

    @Column(name = "override_energia")
    var overrideEnergia: Int? = null,

    @Column(name = "override_independencia")
    var overrideIndependencia: Int? = null,

    @Column(name = "override_sociable_niños")
    var overrideSociableNiños: Int? = null,

    @Column(name = "override_sociable_mascotas")
    var overrideSociableMascotas: Int? = null
)