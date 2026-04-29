package com.kernel.crew.sys.adogta.dto.request

/**
 * DTO que representa la petición para restablecer la contraseña usando un token de recuperación.
 *
 * Endpoint: POST /api/usuarios/reset-password
 *
 * Ejemplo de body:
 * {
 *   "token": "token-generado",
 *   "newPassword": "NewPassword123"
 * }
 */
data class ResetPasswordRequest(

    /** 
     * Token de recuperación enviado al correo del usuario.
     * */
    val token: String,

    /** 
     * Nueva contraseña en texto plano (se hasheará) 
     * */
    val newPassword: String
)