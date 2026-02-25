package com.kernel.crew.sys.adogta.dto.request

/**
 * DTO que representa el cuerpo de la petición de actualización de perfil.
 *
 * Endpoint: PUT /usuarios
 *
 * Ejemplo de body:
 * {
 *   "nombre": "Admin Actualizado",
 *   "apellidoPaterno": "Howard",
 *   "apellidoMaterno": "Benson",
 *   "email": "nuevo@adogta.com",
 *   "teléfono": "5598765432",
 *   "códigoPostal": "11800"
 * }
 */
data class UpdateUsuarioRequest(

    /**
     * Nombre(s) actualizado del usuario.
     * */
    val nombre: String,

    /**
     * Primer apellido actualizado del usuario.
     * */
    val apellidoPaterno: String,

    /**
     * Segundo apellido actualizado del usuario.
     * */
    val apellidoMaterno: String,

    /**
     * Correo electrónico actualizado del usuario.
     * */
    val email: String,

    /**
     * Número telefónico actualizado. Opcional.
     * */
    val telefono: String?,

    /**
     * Código postal actualizado del usuario.
     * */
    val codigoPostal: String
)