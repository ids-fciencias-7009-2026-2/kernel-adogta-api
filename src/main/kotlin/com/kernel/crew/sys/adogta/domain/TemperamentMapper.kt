package com.kernel.crew.sys.adogta.domain

import kotlin.math.roundToInt

/**
 * Convierte rasgos de temperamento (strings libres de The Dog API)
 * a valores numéricos 1–5 para cada atributo de RazaEntity.
 *
 * Flujo:
 *   1. El string "Active, Friendly, Stubborn" se parte por comas.
 *   2. Cada término (en minúsculas) se busca en el mapa del atributo.
 *   3. Los hits se promedian y redondean; sin hits → 3 (valor neutral).
 *
 * Para talla: el string de peso métrico "3 - 6" se parsea a promedio en kg
 * y se clasifica en un bucket 1–5.
 *
 * Para esHipoalergenico en perros: The Dog API no provee el campo,
 * por lo que se consulta el set estático [HYPOALLERGENIC_DOG_BREEDS].
 */
object TemperamentMapper {

    // ─── Mapas por atributo: trait (lowercase) → puntuación 1-5 ─────────────

    /** nivelEnergia: 1 = muy bajo, 5 = muy alto */
    private val ENERGIA: Map<String, Int> = mapOf(
        // 5 – Alta energía
        "active" to 5, "energetic" to 5, "lively" to 5, "vigorous" to 5,
        "boisterous" to 5, "tireless" to 5, "spirited" to 5, "feisty" to 5,
        "frisky" to 5, "excitable" to 5, "spunky" to 5, "dynamic" to 5,
        "restless" to 5, "athletic" to 5, "high-spirited" to 5, "busy" to 5,
        "vivacious" to 5, "high-strung" to 5,
        // 4 – Moderada-alta
        "playful" to 4, "alert" to 4, "responsive" to 4, "agile" to 4,
        "quick" to 4, "outgoing" to 4, "tenacious" to 4, "keen" to 4,
        "adventurous" to 4, "mischievous" to 4, "curious" to 4,
        // 3 – Moderada
        "intelligent" to 3, "confident" to 3, "determined" to 3,
        "obedient" to 3, "trainable" to 3, "faithful" to 3, "loyal" to 3,
        "bold" to 3, "courageous" to 3, "brave" to 3, "fearless" to 3,
        "protective" to 3, "watchful" to 3, "dedicated" to 3,
        "instinctual" to 3, "independent" to 3,
        // 2 – Baja-moderada
        "calm" to 2, "gentle" to 2, "sweet" to 2, "kind" to 2,
        "affectionate" to 2, "devoted" to 2, "loving" to 2, "quiet" to 2,
        "even tempered" to 2, "even-tempered" to 2, "mild" to 2,
        "steady" to 2, "composed" to 2, "reliable" to 2, "trustworthy" to 2,
        // 1 – Muy baja
        "docile" to 1, "lazy" to 1, "laid-back" to 1, "easygoing" to 1,
        "placid" to 1, "sedate" to 1, "relaxed" to 1, "easy going" to 1
    )

    /** sociableNiños: 1 = nada sociable, 5 = muy sociable */
    private val SOCIABLE_NINOS: Map<String, Int> = mapOf(
        // 5
        "gentle" to 5, "friendly" to 5, "affectionate" to 5, "sweet" to 5,
        "kind" to 5, "loving" to 5, "devoted" to 5, "good-natured" to 5,
        "sweet-tempered" to 5, "amiable" to 5, "tender" to 5,
        "cheerful" to 5, "happy" to 5, "fun-loving" to 5, "patient" to 5,
        "merry" to 5, "jovial" to 5, "pleasant" to 5,
        // 4
        "playful" to 4, "trusting" to 4, "loyal" to 4, "sociable" to 4,
        "even tempered" to 4, "even-tempered" to 4, "outgoing" to 4,
        "companionable" to 4, "lively" to 4, "warm" to 4,
        // 3
        "intelligent" to 3, "active" to 3, "alert" to 3, "curious" to 3,
        "energetic" to 3, "confident" to 3, "courageous" to 3,
        "responsive" to 3, "obedient" to 3, "brave" to 3, "calm" to 3,
        // 2
        "stubborn" to 2, "independent" to 2, "willful" to 2, "aloof" to 2,
        "reserved" to 2, "dignified" to 2, "proud" to 2, "serious" to 2,
        "headstrong" to 2, "self-willed" to 2, "dominant" to 2,
        "tenacious" to 2,
        // 1
        "aggressive" to 1, "territorial" to 1, "possessive" to 1,
        "fierce" to 1, "destructive" to 1
    )

    /** sociableMascotas: 1 = nada sociable, 5 = muy sociable */
    private val SOCIABLE_MASCOTAS: Map<String, Int> = mapOf(
        // 5
        "friendly" to 5, "sociable" to 5, "affectionate" to 5,
        "outgoing" to 5, "playful" to 5, "amiable" to 5, "good-natured" to 5,
        "gentle" to 5, "lively" to 5, "cheerful" to 5, "happy" to 5,
        "merry" to 5,
        // 4
        "even tempered" to 4, "even-tempered" to 4, "loyal" to 4,
        "devoted" to 4, "calm" to 4, "sweet" to 4, "trusting" to 4,
        "companionable" to 4, "sweet-tempered" to 4,
        // 3
        "alert" to 3, "curious" to 3, "intelligent" to 3, "active" to 3,
        "energetic" to 3, "courageous" to 3, "confident" to 3,
        "responsive" to 3, "obedient" to 3, "playful" to 3,
        // 2
        "independent" to 2, "aloof" to 2, "reserved" to 2, "dignified" to 2,
        "stubborn" to 2, "headstrong" to 2, "willful" to 2,
        "self-willed" to 2, "tenacious" to 2,
        // 1
        "aggressive" to 1, "territorial" to 1, "dominant" to 1,
        "possessive" to 1, "protective" to 1, "fierce" to 1
    )

    /** independencia: 1 = muy dependiente, 5 = muy independiente */
    private val INDEPENDENCIA: Map<String, Int> = mapOf(
        // 5
        "independent" to 5, "stubborn" to 5, "willful" to 5, "aloof" to 5,
        "self-willed" to 5, "headstrong" to 5, "tenacious" to 5,
        "determined" to 5, "assertive" to 5,
        // 4
        "proud" to 4, "dignified" to 4, "confident" to 4, "courageous" to 4,
        "fearless" to 4, "brave" to 4, "reserved" to 4, "dominant" to 4,
        "protective" to 4, "territorial" to 4, "bold" to 4,
        // 3
        "intelligent" to 3, "curious" to 3, "adventurous" to 3,
        "energetic" to 3, "active" to 3, "mischievous" to 3,
        "lively" to 3, "spirited" to 3, "alert" to 3, "watchful" to 3,
        // 2
        "devoted" to 2, "obedient" to 2, "trainable" to 2, "loyal" to 2,
        "faithful" to 2, "responsive" to 2, "eager" to 2,
        "companionable" to 2, "gregarious" to 2,
        // 1
        "affectionate" to 1, "gentle" to 1, "sweet" to 1, "loving" to 1,
        "friendly" to 1, "sociable" to 1, "cheerful" to 1,
        "people-oriented" to 1, "clingy" to 1
    )

    /**
     * Razas de perro consideradas hipoalergénicas (nombres en minúsculas).
     * The Dog API no incluye este campo, por lo que se mantiene
     * este set estático basado en fuentes veterinarias.
     */
    val HYPOALLERGENIC_DOG_BREEDS: Set<String> = setOf(
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

    data class MappedAttributes(
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
     * @return [MappedAttributes] con valores 1–5 por atributo
     */
    fun mapTemperament(temperament: String): MappedAttributes {
        val traits = temperament.split(",").map { it.trim().lowercase() }
        return MappedAttributes(
            nivelEnergia     = scoreFrom(traits, ENERGIA),
            sociableNinos    = scoreFrom(traits, SOCIABLE_NINOS),
            sociableMascotas = scoreFrom(traits, SOCIABLE_MASCOTAS),
            independencia    = scoreFrom(traits, INDEPENDENCIA)
        )
    }

    /**
     * Convierte el string de peso métrico a talla 1–5.
     *
     * @param weightMetric Ej: "3 - 6" (kg). Acepta también "10" sin rango.
     * @return 1 (miniatura <5 kg) … 5 (gigante >40 kg)
     */
    fun mapWeightToTalla(weightMetric: String): Int {
        val numbers = weightMetric.split("-")
            .mapNotNull { it.trim().toDoubleOrNull() }
        val avg = when {
            numbers.size >= 2 -> (numbers[0] + numbers[1]) / 2.0
            numbers.size == 1 -> numbers[0]
            else -> 15.0 // fallback si el string no es parseable
        }
        return when {
            avg < 5  -> 1
            avg < 12 -> 2
            avg < 25 -> 3
            avg < 40 -> 4
            else     -> 5
        }
    }

    // ─── Privados ───────────────────────────────────────────────────────────

    /**
     * Calcula la puntuación promedio de los traits que aparecen en [scores].
     * Si ningún trait tiene correspondencia, devuelve 3 (neutral).
     */
    private fun scoreFrom(traits: List<String>, scores: Map<String, Int>): Int {
        val hits = traits.mapNotNull { scores[it] }
        return if (hits.isEmpty()) 3 else hits.average().roundToInt()
    }
}
