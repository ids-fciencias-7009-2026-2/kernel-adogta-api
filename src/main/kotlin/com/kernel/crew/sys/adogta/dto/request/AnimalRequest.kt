package com.kernel.crew.sys.adogta.dto.request

data class AnimalRequest(
    val usuarioId: Long, // ID del donante
    val nombre: String,
    val estadoVacunacion: String,
    val esterilizado: Boolean,
    val descripcion: String,
    val entrenado: Boolean,
    val codigoPostal: String,
    val edad: Int,
    val tipo: String, // perro o gato
    val idRaza: Int,
    
    // Los overrides pueden ser null si el usuario no los modificó
    val overrideEnergia: Int? = null,
    val overrideIndependencia: Int? = null,
    val overrideSociableNiños: Int? = null,
    val overrideSociableMascotas: Int? = null
)