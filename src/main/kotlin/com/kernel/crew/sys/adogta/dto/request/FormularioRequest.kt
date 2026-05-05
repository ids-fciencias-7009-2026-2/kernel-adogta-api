package com.kernel.crew.sys.adogta.dto.request

/**
 * DTO que representa el cuerpo de la petición de registro de un nuevo formulario.
 *
 * Endpoint: POST /formulario
 *
 * Ejemplo de body:
 * {
 *   "presupuesto": 3,
 *   "tieneAlergias": 0,
 *   "fechaEnvio": "2023-01-01",
 *   "tieneMascotas": 1,
 *   "tiempoEjercicio": 4,
 *   "tiempoSoledad": 2,
 *   "tieneNiños": 0
 * }
 */


data class FormularioRequest(
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