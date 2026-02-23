package com.kernel.crew.sys.adogta.dto.response

data class UsuarioResponse(
    val id: Long,
    val nombre: String,
    val email: String,
    val codigoPostal: String,
    val telefono: String?,
    val authProvider: String,
    val rol: String,
    val emailVerificado: Boolean,
    val isBaned: Boolean,
    val reputation: Int,
    val aceptaTerminos: Boolean,
    val fechaRegistro: String,
    val ultimoAcceso: String
)