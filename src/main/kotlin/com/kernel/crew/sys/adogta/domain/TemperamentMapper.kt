package com.kernel.crew.sys.adogta.domain

/**
 * Convierte rasgos de temperamento (solo peso y si es hipoalergenico) de 
 * The Dog API a atributos numéricos.
 */
object TemperamentMapper {
    /**
     * Razas de perro consideradas hipoalergénicas (nombres en minúsculas).
     * The Dog API no incluye este campo, por lo que se mantiene
     * este set estático basado en fuentes veterinarias.
     */
    val RAZAS_PERRO_HIPOALERGENICAS: Set<String> = setOf(
        "poodle", "bichon frise", "maltese", "portuguese water dog",
        "miniature schnauzer", "standard schnauzer", "giant schnauzer",
        "yorkshire terrier", "shih tzu", "havanese", "afghan hound",
        "irish water spaniel", "chinese crested", "american hairless terrier",
        "soft coated wheaten terrier", "basenji", "bedlington terrier",
        "kerry blue terrier", "lagotto romagnolo", "coton de tulear",
        "barbet", "bolognese", "xoloitzcuintli", "peruvian inca orchid",
        "spanish water dog", "wire fox terrier", "welsh terrier",
        "sealyham terrier", "lakeland terrier", "west highland white terrier",
        "scottish terrier", "cairn terrier"
    )

    /**
     * Convierte el string de peso métrico a talla 1–5.
     *
     * @param peso Ej: "3 - 6" (kg). Acepta también "10" sin rango.
     * @return 1 (miniatura <5 kg) … 5 (gigante >40 kg)
     */
    fun mapPesoATalla(peso: String): Int {
        val numbers = peso.split("-")
            .mapNotNull { it.trim().toDoubleOrNull() }
        val promedio = when {
            numbers.size >= 2 -> (numbers[0] + numbers[1]) / 2.0
            numbers.size == 1 -> numbers[0]
            else -> 15.0 // fallback si el string no es parseable
        }
        return when {
            promedio < 5  -> 1
            promedio < 12 -> 2
            promedio < 25 -> 3
            promedio < 40 -> 4
            else     -> 5
        }
    }

}
