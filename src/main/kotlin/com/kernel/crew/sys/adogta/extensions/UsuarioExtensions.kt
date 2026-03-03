package com.kernel.crew.sys.adogta.extensions

import com.kernel.crew.sys.adogta.domain.Usuario
import com.kernel.crew.sys.adogta.dto.request.RegisterRequest
import com.kernel.crew.sys.adogta.dto.response.UsuarioResponse
import kotlin.String

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
        nombres = this.nombres,
        apellidoPaterno = this.apellidoPaterno,
        apellidoMaterno = this.apellidoMaterno,
        email = this.email,
        codigoPostal = this.codigoPostal,
        telefono = this.telefono,
        googleId = this.googleId,
        contrasena = this.contrasena,
        aceptaTerminos = this.aceptaTerminos,
        fechaAceptaTerminos = null,
        proveedorAutenticacion = this.proveedorAutenticacion,
        tokenSesion = "NULL",
        fechaExpiracionSesion =  "2026-02-23",
        tokenRecuperacionContrasena = "2026-02-23",
        fechaExpiracionTokenRecuperacion = "2026-02-23",
        esAdoptante = false,
        esDonante = false
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
        id = 1L,
        nombres = this.nombres,
        apellidoPaterno = this.apellidoPaterno,
        apellidoMaterno = this.apellidoMaterno,
        email = this.email,
        codigoPostal = this.codigoPostal,
        telefono = this.telefono,
        proveedorAutenticacion = this.proveedorAutenticacion,
        aceptaTerminos = this.aceptaTerminos,
    )
}