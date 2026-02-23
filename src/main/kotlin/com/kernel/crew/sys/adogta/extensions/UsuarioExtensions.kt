package com.kernel.crew.sys.adogta.extensions

import com.kernel.crew.sys.adogta.domain.Usuario
import com.kernel.crew.sys.adogta.dto.request.RegisterRequest
import com.kernel.crew.sys.adogta.dto.response.UsuarioResponse

fun RegisterRequest.toDomain(): Usuario {
    return Usuario(
        id = 1L,
        nombre = this.nombre,
        email = this.email,
        password = this.password
    )
}

fun Usuario.toResponse(): UsuarioResponse {
    return UsuarioResponse(
        id = this.id,
        nombre = this.nombre,
        email = this.email
    )
}