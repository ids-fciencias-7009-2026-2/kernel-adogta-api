package com.kernel.crew.sys.adogta.dto.request

import com.kernel.crew.sys.adogta.entities.UsuarioEntity
import com.kernel.crew.sys.adogta.enums.Presupuesto
import com.kernel.crew.sys.adogta.enums.TiempoEjercicio
import com.kernel.crew.sys.adogta.enums.TiempoSoledad
import com.kernel.crew.sys.adogta.enums.TieneAlergias
import com.kernel.crew.sys.adogta.enums.TieneMascotas
import com.kernel.crew.sys.adogta.enums.TieneNiños

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

    val presupuesto: Presupuesto,

    /**
     * Si el usario tiene o no alergias.
     */

    val tieneAlergias: TieneAlergias,

    /**
     * Fecha en la que se envió el formulario.
     */

    val fechaEnvio: String,

    /**
     * Si es usuario tiene o no mascotas.
     */

    val tieneMascotas: TieneMascotas,

    /**
     *El tiempo que el usuario pasa haciendo ejercicio.
     */

    val tiempoEjercicio: TiempoEjercicio,

    /**
     *El tiempo que la mascota estará sola.
     */

    val tiempoSoledad: TiempoSoledad,

    /**
     * Si el usuario tiene o no niños.
     */

    val tieneNiños: TieneNiños
)