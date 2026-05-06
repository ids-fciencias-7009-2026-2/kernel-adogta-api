package com.kernel.crew.sys.adogta.dto.response

/**
 * DTO que representa la respuesta con información del formulario.
 */
data class FormularioResponse(

    /**
     * Identificador único del formulario.
     * */
    val id: Long,

    /**
     * Presupuesto del usuario.
     * */
    val presupuesto: Int,

    /**
     * Indica si el usuario tiene alergias.
     * */
    val tieneAlergias: Int,

    /**
     * Fecha en que se envió el formulario.
     * */
    val fechaEnvio: String,

    /**
     * Indica si el usuario tiene mascotas actualmente.
     * */
    val tieneMascotas: Int,

    /**
     * Tiempo que el usuario hace ejercicio.
     * */
    val tiempoEjercicio: Int,

    /**
     * Tiempo diario que la mascota estará sola.
     * */
    val tiempoSoledad: Int,

    /**
     * Indica si el usuario tiene niños.
     * */
    val tieneNiños: Int
)