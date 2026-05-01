package com.kernel.crew.sys.adogta.dto.response

/**
 * DTO genérico de respuesta del backend. Es un mensaje informativo.
 * 
 * Se usará donde no se requiera devolver una entidad.
 * 
 * Ejemplo:
 * {
 *   "message": "Contraseña restablecida exitosamente."
 * }
 */
data class MessageResponse(
    val message: String
)