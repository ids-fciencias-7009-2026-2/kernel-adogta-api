package com.kernel.crew.sys.adogta.dto.response

data class InteresadoResponse(
    val idSolicitud: Int,
    val idUsuario: Int,
    val nombre: String,
    val email: String,
    val telefono: String?
)