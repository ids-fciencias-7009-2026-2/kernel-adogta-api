package com.kernel.crew.sys.adogta.dto.response

/**
 * DTO que representa la respuesta con información de una solicitud de adopción.
 *
 * Excluye datos internos de la entidad.
 */
data class SolicitudResponse(

    /** Identificador único de la solicitud. */
    val idSolicitud: Int,

    /** Fecha en que se registró la solicitud. */
    val fecha: String,

    /** Estado actual: Pendiente, Aprobada, Rechazada o Cancelada. */
    val estado: String,

    /** ID del animal al que corresponde la solicitud. */
    val idAnimal: Int,

    /** ID de la publicación del animal. */
    val idPublicacion: Int,

    /** ID del usuario donante dueño del animal. */
    val idUsuarioAnimal: Int,

    /** Mensaje informativo para el cliente. */
    val mensaje: String = "Solicitud enviada exitosamente"
)