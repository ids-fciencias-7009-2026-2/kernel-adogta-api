package com.kernel.crew.sys.adogta.dto.request

/**
 * DTO para reportar una publicación.
 *
 * Endpoint: POST /api/reportes
 */
data class ReporteRequest(
    /** ID de la publicación a reportar. */
    val idPublicacion: Int,
    /** Motivo del reporte. */
    val motivo: String
)