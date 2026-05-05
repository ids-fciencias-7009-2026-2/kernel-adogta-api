package com.kernel.crew.sys.adogta.dto.response

data class AnimalResponse(
    val idAnimal: Int?,
    val idPublicacion: Int?,
    val nombre: String,
    val mensaje: String = "¡Animal publicado exitosamente!"
)