package com.kernel.crew.sys.adogta.dto.request

/**
 * DTO que representa la petición para solicitar la recuperación de contraseña.
 *
 * Endpoint: POST /api/usuarios/forgot-password
 *
 * Ejemplo de body:
 * {
 *   "email": "usuario@adogta.com"
 * }
 */
data class ForgotPasswordRequest(

    /** Correo asociado a la cuenta cuya contraseña se desea recuperar. */
    val email: String
)