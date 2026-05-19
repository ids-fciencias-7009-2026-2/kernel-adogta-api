package com.kernel.crew.sys.adogta.domain

import kotlin.math.roundToInt

/**
 * Convierte rasgos de temperamento (strings libres de The Dog API)
 * a valores numéricos 1–5 para cada atributo de RazaEntity.
 */
object TemperamentMapper {

    // ─── Mapas por atributo: trait (lowercase) → puntuación 1-5 ─────────────

    /** nivelEnergia: 1 = muy bajo, 5 = muy alto */
    private val ENERGIA: Map<String, Int> = mapOf(
        // 5 – Alta energía
        listOf(
            "active", "energetic", "lively", "vigorous", "boisterous", 
            "tireless", "spirited", "feisty", "frisky", "excitable", 
            "spunky", "dynamic", "restless", "athletic", "high-spirited", 
            "busy", "vivacious", "high-strung"
        ) to 5,
        // 4 – Moderada-alta
        listOf(
            "playful", "alert", "responsive", "agile", "quick", 
            "outgoing", "tenacious", "keen", "adventurous", 
            "mischievous", "curious"
        ) to 4,
        // 3 – Moderada
        listOf(
            "intelligent", "confident", "determined", "obedient", 
            "trainable", "faithful", "loyal", "bold", "courageous", 
            "brave", "fearless", "protective", "watchful", "dedicated", 
            "instinctual", "independent"
        ) to 3,
        // 2 – Baja-moderada
        listOf(
            "calm", "gentle", "sweet", "kind", "affectionate", "devoted", 
            "loving", "quiet", "even tempered", "even-tempered", "mild", 
            "steady", "composed", "reliable", "trustworthy"
        ) to 2,
        // 1 – Muy baja
        listOf(
            "docile", "lazy", "laid-back", "easygoing", "placid", 
            "sedate", "relaxed", "easy going"
        ) to 1
    ).flatMap { (palabras, valor) -> palabras.map { it to valor } }.toMap()

    /** sociableNiños: 1 = nada sociable, 5 = muy sociable */
    private val SOCIABLE_NINOS: Map<String, Int> = mapOf(
        // 5
        listOf(
            "gentle", "friendly", "affectionate", "sweet", "kind", 
            "loving", "devoted", "good-natured", "sweet-tempered", 
            "amiable", "tender", "cheerful", "happy", "fun-loving", 
            "patient", "merry", "jovial", "pleasant"
        ) to 5,
        // 4
        listOf(
            "playful", "trusting", "loyal", "sociable", "even tempered", 
            "even-tempered", "outgoing", "companionable", "lively", "warm"
        ) to 4,
        // 3
        listOf(
            "intelligent", "active", "alert", "curious", "energetic", 
            "confident", "courageous", "responsive", "obedient", 
            "brave", "calm"
        ) to 3,
        // 2
        listOf(
            "stubborn", "independent", "willful", "aloof", "reserved", 
            "dignified", "proud", "serious", "headstrong", "self-willed", 
            "dominant", "tenacious"
        ) to 2,
        // 1
        listOf(
            "aggressive", "territorial", "possessive", "fierce", "destructive"
        ) to 1
    ).flatMap { (palabras, valor) -> palabras.map { it to valor } }.toMap()

    /** sociableMascotas: 1 = nada sociable, 5 = muy sociable */
    private val SOCIABLE_MASCOTAS: Map<String, Int> = mapOf(
        // 5
        listOf(
            "friendly", "sociable", "affectionate", "outgoing", "playful", 
            "amiable", "good-natured", "gentle", "lively", "cheerful", 
            "happy", "merry"
        ) to 5,
        // 4
        listOf(
            "even tempered", "even-tempered", "loyal", "devoted", "calm", 
            "sweet", "trusting", "companionable", "sweet-tempered"
        ) to 4,
        // 3
        listOf(
            "alert", "curious", "intelligent", "active", "energetic", 
            "courageous", "confident", "responsive", "obedient"
            // "playful" fue removido de aquí para evitar la duplicidad con el nivel 5
        ) to 3,
        // 2
        listOf(
            "independent", "aloof", "reserved", "dignified", "stubborn", 
            "headstrong", "willful", "self-willed", "tenacious"
        ) to 2,
        // 1
        listOf(
            "aggressive", "territorial", "dominant", "possessive", 
            "protective", "fierce"
        ) to 1
    ).flatMap { (palabras, valor) -> palabras.map { it to valor } }.toMap()

    /** independencia: 1 = muy dependiente, 5 = muy independiente */
    private val INDEPENDENCIA: Map<String, Int> = mapOf(
        // 5
        listOf(
            "independent", "stubborn", "willful", "aloof", "self-willed", 
            "headstrong", "tenacious", "determined", "assertive"
        ) to 5,
        // 4
        listOf(
            "proud", "dignified", "confident", "courageous", "fearless", 
            "brave", "reserved", "dominant", "protective", "territorial", "bold"
        ) to 4,
        // 3
        listOf(
            "intelligent", "curious", "adventurous", "energetic", "active", 
            "mischievous", "lively", "spirited", "alert", "watchful"
        ) to 3,
        // 2
        listOf(
            "devoted", "obedient", "trainable", "loyal", "faithful", 
            "responsive", "eager", "companionable", "gregarious"
        ) to 2,
        // 1
        listOf(
            "affectionate", "gentle", "sweet", "loving", "friendly", 
            "sociable", "cheerful", "people-oriented", "clingy"
        ) to 1
    ).flatMap { (palabras, valor) -> palabras.map { it to valor } }.toMap()

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

    // ─── Resultado del mapeo ────────────────────────────────────────────────

    data class AtributosMapeados(
        val nivelEnergia: Int,
        val sociableNinos: Int,
        val sociableMascotas: Int,
        val independencia: Int
    )

    // ─── API pública ────────────────────────────────────────────────────────

    /**
     * Convierte el campo `temperament` de The Dog API a atributos numéricos.
     *
     * @param temperament Ej: "Active, Friendly, Stubborn, Curious"
     * @return [AtributosMapeados] con valores 1–5 por atributo
     */
    fun mapearAtributos(temperamento: String): AtributosMapeados {
        val rasgos = temperamento.split(",").map { it.trim().lowercase() }
        return AtributosMapeados(
            nivelEnergia     = puntuacion(rasgos, ENERGIA),
            sociableNinos    = puntuacion(rasgos, SOCIABLE_NINOS),
            sociableMascotas = puntuacion(rasgos, SOCIABLE_MASCOTAS),
            independencia    = puntuacion(rasgos, INDEPENDENCIA)
        )
    }

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

    // ─── Privados ───────────────────────────────────────────────────────────

    /**
     * Calcula la puntuación promedio de los traits que aparecen en [scores].
     * Si ningún trait tiene correspondencia, devuelve 3 (neutral).
     */
    private fun puntuacion(rasgos: List<String>, puntos: Map<String, Int>): Int {
        val puntuacion = rasgos.mapNotNull { puntos[it] }
        return if (puntuacion.isEmpty()) 3 else puntuacion.average().roundToInt()
    }
}
