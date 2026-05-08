package com.kernel.crew.sys.adogta.dto.request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

/**
 * DTO que representa el cuerpo de la petición para crear una solicitud de adopción.
 *
 * Endpoint: POST /api/solicitudes
 */
data class SolicitudRequest(

    @field:NotNull(message = "El id del animal es obligatorio.")
    @field:Min(value = 1, message = "El id del animal debe ser positivo.")
    val idAnimal: Int,

    @field:NotNull(message = "El id de la publicación es obligatorio.")
    @field:Min(value = 1, message = "El id de la publicación debe ser positivo.")
    val idPublicacion: Int,

    @field:NotNull(message = "El id del usuario dueño del animal es obligatorio.")
    @field:Min(value = 1, message = "El id del usuario del animal debe ser positivo.")
    val idUsuarioAnimal: Int
)