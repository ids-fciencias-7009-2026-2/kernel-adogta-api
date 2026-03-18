package com.kernel.crew.sys.adogta.dto.request

/**
 * DTO que representa el cuerpo de la petición de registro de un nuevo usuario.
 *
 * Endpoint: POST /usuarios/register
 *
 * Ejemplo de body:
 * {
 *   "nombres": "Ana",
 *   "apellidoPaterno": "López",
 *   "apellidoMaterno": "García",
 *   "email": "ana@adogta.com",
 *   "codigoPostal": "06600",
 *   "telefono": "5512345678",
 *   "contraseña": "segura123",
 *   "googleId": null,
 *   "proveedorAutenticacion": "local",
 *   "aceptaTerminos": true,
 *   "esAdoptante": true,
 *   "esDonante": false
 * }
 */
data class RegisterRequest(

    /**
     * Nombre(s) del usuario.
     * */
    val nombres: String,

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
    val contrasena: String?,

    /**
     *  Identificador de cuenta Google. Null si el proveedor es local.
     *  */
    val googleId: String?,

    /**
     *  Proveedor de autenticación: "local" o "google".
     *  */
    val proveedorAutenticacion: String,

    /**
     * Indica si el usuario aceptó los términos y condiciones.
     * */
    val aceptaTerminos: Boolean,

    /**
     * Indica si el usuario se registra como adoptante.
     * */
    val esAdoptante: Boolean,

    /**
     * Indica si el usuario se registra como donante
     * */
    val esDonante: Boolean
)