package com.kernel.crew.sys.adogta.extensions

import com.kernel.crew.sys.adogta.domain.Usuario
import com.kernel.crew.sys.adogta.dto.request.RegisterRequest
import com.kernel.crew.sys.adogta.dto.response.UsuarioResponse

/**
 * Extension functions para conversión entre capas del sistema.
 *
 * Permiten transformar objetos entre la capa de transporte (DTOs)
 * y la capa de dominio, manteniendo separadas las responsabilidades.
 */

/**
 * Convierte un [RegisterRequest] en un objeto de dominio [Usuario].
 *
 * Asigna valores default correspondientes a un usuario recién registrado:
 * rol "usuario", reputación 0, email no verificado, sin baneo.
 *
 * Nota: id y las fechas son simulados hasta integrar base de datos.
 */
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

/**
 * Convierte un objeto de dominio [Usuario] en un [UsuarioResponse].
 *
 * Excluye campos sensibles como password, googleId, tokens
 * y datos internos de auditoría.
 */
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