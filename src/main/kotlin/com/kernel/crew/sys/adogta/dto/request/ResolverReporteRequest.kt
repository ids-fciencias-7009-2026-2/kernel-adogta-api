package com.kernel.crew.sys.adogta.dto.request

/**
 * DTO para resolver un reporte.
 *
 * Endpoint: PUT /api/admin/reportes/{idReporte}/resolver
 */
data class ResolverReporteRequest(
    /** Acción a tomar: desestimar o bajar publicación. */
    val accion: String
)