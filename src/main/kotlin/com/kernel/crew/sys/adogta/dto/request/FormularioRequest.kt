package com.kernel.crew.sys.adogta.dto.request

import com.kernel.crew.sys.adogta.entities.UsuarioEntity

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
     * Usuario que envía el formulario.
     */
    val usuario: UsuarioEntity,

    /**
     * Presupuesto del usuario.
     * */

    val presupuesto: Int,

    /**
     * Si el usario tiene o no alergias.
     */

    val tieneAlergias: Int,

    /**
     * Fecha en la que se envió el formulario.
     */

    val fechaEnvio: String,

    /**
     * Si es usuario tiene o no mascotas.
     */

    val tieneMascotas: Int,

    /**
     *El tiempo que el usuario pasa haciendo ejercicio.
     */

    val tiempoEjercicio: Int,

    /**
     *El tiempo que la mascota estará sola.
     */

    val tiempoSoledad: Int,

    /**
     * Si el usuario tiene o no niños.
     */

    val tieneNiños: Int
)