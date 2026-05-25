package com.kernel.crew.sys.adogta.dto.response

import java.time.LocalDate

/**
 * DTO con la información de un reporte.
 */
data class ReporteResponse(
    /** ID del reporte. */
    val idReporte: Long,
    /** ID de la publicación reportada. */
    val idPublicacion: Int,
    /** ID del animal */
    val idAnimal: Int,
    /** Nombre del animal de la publicación. */
    val nombreAnimal: String,
    /** Motivo del reporte. */
    val motivo: String,
    /** Fecha en que se registró el reporte. */
    val fecha: LocalDate,
    /** Estado del reporte (Pendiente, Desestimado,...). */
    val estado: String
)