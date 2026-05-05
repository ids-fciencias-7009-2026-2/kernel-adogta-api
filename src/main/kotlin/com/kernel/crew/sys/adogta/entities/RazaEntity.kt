package com.kernel.crew.sys.adogta.entities

import jakarta.persistence.*

@Entity
@Table(name = "Raza")
class RazaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_raza")
    val id: Int? = null,

    val talla: Int,

    val independencia: Int,

    @Column(name = "nivel_energia")
    val nivelEnergia: Int,

    @Column(columnDefinition = "TEXT")
    val personalidad: String,

    @Column(name = "sociable_niños")
    val sociableNiños: Int,

    @Column(name = "sociable_mascotas")
    val sociableMascotas: Int,

    @Column(name = "es_hipoalergenico")
    val esHipoalergenico: Int
)