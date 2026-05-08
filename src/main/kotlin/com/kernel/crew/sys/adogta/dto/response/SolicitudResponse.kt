package com.kernel.crew.sys.adogta.dto.response

import java.time.LocalDate

data class SolicitudResponse(
    val idSolicitud: Int,
    val idUsuario: Int,
    val idAnimal: Int,
    val idPublicacion: Int,
    val idUsuarioAnimal: Int,
    val estado: String,
    val fecha: LocalDate
)
