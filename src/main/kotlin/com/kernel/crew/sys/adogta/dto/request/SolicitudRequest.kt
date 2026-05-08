package com.kernel.crew.sys.adogta.dto.request

import jakarta.validation.constraints.PositiveOrZero

data class SolicitudRequest(
    @field:PositiveOrZero(message = "El id del animal no puede ser negativo.")
    val idAnimal: Int,

    @field:PositiveOrZero(message = "El id de la publicación no puede ser negativo.")
    val idPublicacion: Int,

    @field:PositiveOrZero(message = "El id del usuario donante no puede ser negativo.")
    val idUsuarioAnimal: Int
)
