package com.kernel.crew.sys.adogta.enums

/** Archivo creado para majear los enums que se encargaran
 * de "mapear" las respuestas del cuestionario a lo valores
 * númericos correspondientes.
 */

enum class TiempoEjercicio(val valor: Int) {
    SEDENTARIO(1),
    MODERADO(3),
    INTENSO(5);
}

enum class TiempoSoledad(val valor: Int) {
    MENOS_DE_4H(1),
    DE_4H_A_8H(3),
    MAS_DE_8H(5),
}

enum class TieneNiños(val valor: Int) {
    SI(1),
    NO(0)
}

enum class TieneMascotas(val valor: Int) {
    SI(1),
    NO(0)
}

enum class TieneAlergias(val valor: Int) {
    SI(1),
    NO(0)
}

enum class Presupuesto(val valor: Int) {
    MENOS_DE_10K(1),
    DE_10K_A_20K(3),
    MAS_DE_20K(5)
}