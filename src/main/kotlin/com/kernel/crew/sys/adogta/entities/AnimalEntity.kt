package com.kernel.crew.sys.adogta.entities

import jakarta.persistence.*

@Entity
@Table(name = "animales") // tabla de bdd
class AnimalEntity(
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null, // PK

    @Column(nullable = false)
    var nombre: String, // nombre del animal

    @Column(nullable = false)
    var especie: String, // Perro o gato

    @Column(nullable = false)
    var edad: String, // edad de animal

    @Column(nullable = false)
    var tamano: String, // pequeño, mediano o grande

    @Column(columnDefinition = "TEXT")
    var descripcion: String, // descripcion o cosas importantes qué destacas

    @Column(name = "foto_url")
    var fotoUrl: String? = null,
 
    // publicador
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    var publicador: UsuarioEntity
)