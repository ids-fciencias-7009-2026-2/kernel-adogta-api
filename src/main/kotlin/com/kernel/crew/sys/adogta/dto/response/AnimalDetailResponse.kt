package com.kernel.crew.sys.adogta.dto.response

/**
 * DTO para devolver los datos editables de un animal.
 */
data class AnimalDetailResponse(
    val idAnimal: Int,
    val nombre: String,
    val tipo: String,
    val edad: Int,
    val descripcion: String,
    val codigoPostal: String,
    val estadoVacunacion: String,
    val esterilizado: Boolean,
    val entrenado: Boolean,
    val idRaza: Int,
    val overrideEnergia: Int?,
    val overrideIndependencia: Int?,
    val overrideSociableNiños: Int?,
    val overrideSociableMascotas: Int?,
    val padecimientos: Set<String>,
    val fotos: Set<String>
)