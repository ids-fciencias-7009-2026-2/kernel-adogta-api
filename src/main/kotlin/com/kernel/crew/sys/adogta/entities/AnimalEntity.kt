package com.kernel.crew.sys.adogta.entities

import jakarta.persistence.*

@Entity
@Table(name = "Animal")
class AnimalEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_animal")
    val id: Int? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_publicacion", nullable = false)
    val publicacion: PublicacionEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
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
    var raza: RazaEntity,

    // override para la personalidad
    @Column(name = "override_energia")
    var overrideEnergia: Int? = null,

    @Column(name = "override_independencia")
    var overrideIndependencia: Int? = null,

    @Column(name = "override_sociable_niños")
    var overrideSociableNiños: Int? = null,

    @Column(name = "override_sociable_mascotas")
    var overrideSociableMascotas: Int? = null
)