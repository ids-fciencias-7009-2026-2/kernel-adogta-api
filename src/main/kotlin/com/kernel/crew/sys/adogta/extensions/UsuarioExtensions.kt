package com.kernel.crew.sys.adogta.extensions

import com.kernel.crew.sys.adogta.domain.Usuario
import com.kernel.crew.sys.adogta.dto.request.RegisterRequest
import com.kernel.crew.sys.adogta.dto.response.UsuarioResponse

fun RegisterRequest.toDomain(): Usuario {
    return Usuario(
        id = 1L,
        nombre = this.nombre,
        apellidoPaterno = this.apellidoPaterno,
        apellidoMaterno = this.apellidoMaterno,
        email = this.email,
        codigoPostal = this.codigoPostal,
        telefono = this.telefono,
        password = this.password,
        googleId = this.googleId,
        authProvider = this.authProvider,
        rol = "usuario",
        emailVerificado = false,
        isBaned = false,
        banMotive = null,
        banDate = null,
        bannedBy = null,
        reputation = 0,
        aceptaTerminos = this.aceptaTerminos,
        fechaAceptaTerminos = null,
        fechaRegistro = "2026-02-23",
        ultimoAcceso = "2026-02-23",
        fechaUpdate = "2026-02-23"
    )
}

fun Usuario.toResponse(): UsuarioResponse {
    return UsuarioResponse(
        id = this.id,
        nombre = this.nombre,
        apellidoPaterno = this.apellidoPaterno,
        apellidoMaterno = this.apellidoMaterno,
        email = this.email,
        codigoPostal = this.codigoPostal,
        telefono = this.telefono,
        authProvider = this.authProvider,
        rol = this.rol,
        emailVerificado = this.emailVerificado,
        isBaned = this.isBaned,
        reputation = this.reputation,
        aceptaTerminos = this.aceptaTerminos,
        fechaRegistro = this.fechaRegistro,
        ultimoAcceso = this.ultimoAcceso
    )
}