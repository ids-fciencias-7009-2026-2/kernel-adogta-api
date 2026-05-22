package com.kernel.crew.sys.adogta.dto.request

/**
 * DTO que representa la petición para solicitar una petición al microservicio que maneja el mapeo de rasgos.
 *
 * Endpoint: POST /clasificar-temperamento
 *
 * Ejemplo de body:
 * {
 *   "texto_a_clasificar": "Affectionate, playful, friendly, charming, adaptable"
 * }
 */

data class LlmMapRequest(
    val texto_a_clasificar: String
)