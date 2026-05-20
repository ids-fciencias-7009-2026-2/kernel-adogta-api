package com.kernel.crew.sys.adogta.dto.request

/**
 * DTO para banear a un usuario.
 *
 * Endpoint: POST /api/admin/baneos
 */
data class BanRequest(
    /** ID del usuario a banear. */
    val idUsuario: Long,
    /** Motivo del baneo. */
    val motivo: String
)