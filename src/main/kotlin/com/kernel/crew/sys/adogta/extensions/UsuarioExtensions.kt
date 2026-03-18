package com.kernel.crew.sys.adogta.extensions

import com.kernel.crew.sys.adogta.domain.Usuario
import com.kernel.crew.sys.adogta.dto.request.RegisterRequest
import com.kernel.crew.sys.adogta.dto.response.UsuarioResponse
import com.kernel.crew.sys.adogta.entities.UsuarioEntity
import java.security.MessageDigest
import kotlin.String

/**
 * Extension functions para conversión entre capas del sistema.
 *
 * Permiten transformar objetos entre:
 *  - Capa de transpote (DTOs) → Capa de persistencia (Entity)
 *  - Capa de persistencia (Entity) → Capa de dominio (Domain)
 *  - Capa de dominio (Domain) → Capa de transporte (Response)
 */

/**
 * Convierte un [RegisterRequest] en un [UsuarioEntity] listo para persistir.
 *
 * Hashea la contraseña con SHA-256 antes de almacenarla.
 * Asigna valores por defecto para campos que aún no aplican
 * al momento del registro (tokens, sesión, roles).
 */
fun RegisterRequest.toEntity(): UsuarioEntity {
    return UsuarioEntity(
        nombres = this.nombres,
        apellidoPaterno = this.apellidoPaterno,
        apellidoMaterno = this.apellidoMaterno,
        email = this.email,
        codigoPostal = this.codigoPostal,
        telefono = this.telefono,
        googleId = this.googleId,
        contrasena = this.contrasena?.let { hashPassword(it) },
        aceptaTerminos = this.aceptaTerminos,
        fechaAceptaTerminos = null,
        proveedorAutenticacion = this.proveedorAutenticacion,
        tokenSesion = null,
        fechaExpiracionSesion = null,
        tokenRecuperacionContrasena = null,
        fechaExpiracionTokenRecuperacion = null,
        esAdoptante = this.esAdoptante,
        esDonante = this.esDonante
    )
}

/**
 * Convierte un objeto de dominio [UsuarioEntity] en un objeto de dominio [Usuario].
 *
 * Se usa internamente en el service para trabajar con la lógica de negocio
 * sin depender directamente de la capa de persistencia.
 */
fun UsuarioEntity.toDomain(): Usuario {
    return Usuario(
        id = this.id,
        nombres = this.nombres,
        apellidoPaterno = this.apellidoPaterno,
        apellidoMaterno = this.apellidoMaterno,
        email = this.email,
        codigoPostal = this.codigoPostal,
        telefono = this.telefono,
        googleId = this.googleId,
        contrasena = this.contrasena,
        aceptaTerminos = this.aceptaTerminos,
        fechaAceptaTerminos = this.fechaAceptaTerminos?.toString(),
        proveedorAutenticacion = this.proveedorAutenticacion,
        tokenSesion = this.tokenSesion,
        fechaExpiracionSesion = this.fechaExpiracionSesion?.toString(),
        tokenRecuperacionContrasena = this.tokenRecuperacionContrasena,
        fechaExpiracionTokenRecuperacion = this.fechaExpiracionTokenRecuperacion?.toString(),
        esAdoptante = this.esAdoptante,
        esDonante = this.esDonante
    )
}

/**
 * Convierte un objeto de dominio [Usuario] en un [UsuarioResponse].
 *
 * Excluye campos sensibles como contrasena, googleId, tokens
 * y datos internos de sesión.
 */
fun Usuario.toResponse(): UsuarioResponse {
    return UsuarioResponse(
        id = this.id,
        nombres = this.nombres,
        apellidoPaterno = this.apellidoPaterno,
        apellidoMaterno = this.apellidoMaterno,
        email = this.email,
        codigoPostal = this.codigoPostal,
        telefono = this.telefono,
        proveedorAutenticacion = this.proveedorAutenticacion,
        aceptaTerminos = this.aceptaTerminos
    )
}

/**
 * Genera el hash SHA-256 de una contraseña en texto plano.
 *
 * @param password Contraseña en texto plano.
 * @return Representación hexadecimal del hash SHA-256.
 */
private fun hashPassword(password: String): String {
    val bytes = MessageDigest
        .getInstance("SHA-256")
        .digest(password.toByteArray())
    return bytes.joinToString("") { "%02x".format(it) }
}