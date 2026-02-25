package com.kernel.crew.sys.adogta.dto.request

/**
 * DTO que representa el cuerpo de la petición de registro de un nuevo usuario.
 *
 * Endpoint: POST /usuarios/register
 *
 * Ejemplo de body:
 * {
 *   "nombre": "Ana",
 *   "apellidoPaterno": "López",
 *   "apellidoMaterno": "García",
 *   "email": "ana@adogta.com",
 *   "códigoPostal": "06600",
 *   "teléfono": "5512345678",
 *   "password": "segura123",
 *   "googleId": null,
 *   "authProvider": "local",
 *   "aceptaTérminos": true
 * }
 */
data class RegisterRequest(

    /**
     * Nombre(s) del usuario.
     * */
    val nombre: String,

    /**
     * Primer apellido del usuario.
     * */
    val apellidoPaterno: String,

    /**
     * Segundo apellido del usuario.
     * */
    val apellidoMaterno: String,

    /**
     * Correo electrónico único del usuario.
     * */
    val email: String,

    /**
     * Código postal del usuario.
     * */
    val codigoPostal: String,

    /**
     * Número telefónico del usuario. Opcional.
     * */
    val telefono: String?,

    /**
     * Contraseña en texto plano. Null si el proveedor es Google.
     * */
    val password: String?,

    /**
     *  Identificador de cuenta Google. Null si el proveedor es local.
     *  */
    val googleId: String?,

    /**
     *  Proveedor de autenticación: "local" o "google".
     *  */
    val authProvider: String,

    /**
     * Indica si el usuario aceptó los términos y condiciones.
     * */
    val aceptaTerminos: Boolean
)