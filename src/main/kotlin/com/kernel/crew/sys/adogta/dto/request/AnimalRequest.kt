package com.kernel.crew.sys.adogta.dto.request

data class AnimalRequest(
    val usuarioId: Int,
    val nombre: String,
    val tipo: String,
    val edad: Int,
    val descripcion: String,
    val codigoPostal: String,
    val estadoVacunacion: String,
    val esterilizado: Boolean,
    val entrenado: Boolean,
    val idRaza: Int,
    val overrideEnergia: Int? = null,
    val overrideIndependencia: Int? = null,
    val overrideSociableNinos: Int? = null,
    val overrideSociableMascotas: Int? = null
)