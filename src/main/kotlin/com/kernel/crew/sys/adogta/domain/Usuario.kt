package com.kernel.crew.sys.adogta.domain

data class Usuario(
    val id: Long,
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val email: String,
    val codigoPostal: String,
    val telefono: String?,
    val password: String?,
    val googleId: String?,
    val authProvider: String,
    val rol: String,
    val emailVerificado: Boolean,
    val isBaned: Boolean,
    val banMotive: String?,
    val banDate: String?,
    val bannedBy: Long?,
    val reputation: Int,
    val aceptaTerminos: Boolean,
    val fechaAceptaTerminos: String?,
    val fechaRegistro: String,
    val ultimoAcceso: String,
    val fechaUpdate: String
)