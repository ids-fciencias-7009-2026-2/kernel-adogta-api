package com.kernel.crew.sys.adogta.domain

/**
 * Modelo de dominio que representa a un Formulario dentro del sistema Adogta.
 */

 data class Formulario(
    /**
     * Identificador único del formulario.
     */
    val id: Long,

    /**
     * Identificador del usuario al que pertenece el formulario.
     */
    val idUsuario: Long,

    /**
     * Presupuesto del usuario.
     * */

    val presupuesto: Integer,

    /**
     * Si el usario tiene o no alergias.
     */

    val tieneAlergias: Integer,

    /**
     * Fecha en la que se envió el formulario.
     */

    val fechaEnvio: String,

    /**
     * Si es usuario tiene o no mascotas.
     */

    val tieneMascotas: Integer,

    /**
     *El tiempo que el usuario pasa haciendo ejercicio.
     */

    val tiempoEjercicio: Integer,

    /**
     *El tiempo que la mascota estará sola.
     */

    val tiempoSoledad: Integer,

    /**
     * Si el usuario tiene o no niños.
     */

    val tieneNiños: Integer

 )