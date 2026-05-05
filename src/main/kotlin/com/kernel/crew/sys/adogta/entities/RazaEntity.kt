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
    val nivel_energia: Int,
    
    @Column(columnDefinition = "TEXT")
    val personalidad: String,
    
    val sociable_niños: Int,
    val sociable_mascotas: Int,
    val es_hipoalergenico: Int
)