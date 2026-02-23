package com.kernel.crew.sys.adogta.dto.request

data class RegisterRequest(
    val nombre: String,
    val email: String,
    val codigoPostal: String,
    val telefono: String?,
    val password: String?,
    val googleId: String?,
    val authProvider: String,
    val aceptaTerminos: Boolean
)