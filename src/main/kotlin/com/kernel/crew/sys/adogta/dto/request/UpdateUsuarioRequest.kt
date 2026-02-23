package com.kernel.crew.sys.adogta.dto.request

data class UpdateUsuarioRequest(
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val email: String,
    val telefono: String?,
    val codigoPostal: String
)