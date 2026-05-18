package com.kernel.crew.sys.adogta.dto.response

/**
 * DTO con los datos del formulario, usando labels, no valores numéricos.
 */
data class FormularioStringsResponse(
    
    /**
     * Identificador único del formulario.
     * */
    val id: Long,

    /**
     * Presupuesto del usuario.
     * */
    val presupuesto: String,

    /**
     * Indica si el usuario tiene alergias.
     * */
    val tieneAlergias: String,

    /**
     * Fecha en que se envió el formulario.
     * */
    val fechaEnvio: String,

    /**
     * Indica si el usuario tiene mascotas actualmente.
     * */
    val tieneMascotas: String,

    /**
     * Tiempo que el usuario hace ejercicio.
     * */
    val tiempoEjercicio: String,

    /**
     * Tiempo diario que la mascota estará sola.
     * */
    val tiempoSoledad: String,

    /**
     * Indica si el usuario tiene niños.
     * */
    val tieneNiños: String
)