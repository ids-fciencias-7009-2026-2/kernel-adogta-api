package com.kernel.crew.sys.adogta.dto.request

/**
 * DTO que representa el cuerpo de la petición de autenticación.
 *
 * Endpoint: POST /usuarios/login
 *
 * Ejemplo de body:
 * {
 *   "email": "usuario@adogta.com",
 *   "password": "contraseña"
 * }
 */
data class LoginRequest(

    /** Correo electrónico del usuario. */
    val email: String,

    /** Contraseña en texto plano. Se hashea antes de compararse. */
    val password: String
)