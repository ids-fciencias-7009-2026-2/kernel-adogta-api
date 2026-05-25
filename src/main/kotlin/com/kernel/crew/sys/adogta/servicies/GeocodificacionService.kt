package com.kernel.crew.sys.adogta.servicies

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class GeocodificacionService(
    @Value("\${app.googlemaps.api-key:}") private val googleMapsApiKey: String
) {
    private val logger = LoggerFactory.getLogger(GeocodificacionService::class.java)
    private val restTemplate = RestTemplate()

    // Caché en memoria para evitar múltiples llamadas al mismo CP
    private val cache = mutableMapOf<String, Pair<Double, Double>>()

    fun obtenerCoordenadas(codigoPostal: String): Pair<Double, Double> {
        // 1. Devolver de caché si ya se consultó antes
        cache[codigoPostal]?.let {
            logger.debug("CP $codigoPostal desde caché: $it")
            return it
        }

        // 2. Llamar a Google Geocoding API
        if (googleMapsApiKey.isNotBlank()) {
            try {
                val url = UriComponentsBuilder
                    .fromUriString("https://maps.googleapis.com/maps/api/geocode/json")
                    .queryParam("address", "$codigoPostal, Mexico")
                    .queryParam("key", googleMapsApiKey)
                    .build()
                    .toUri()

                val respuesta = restTemplate.getForObject(url, Map::class.java)
                val status = respuesta?.get("status") as? String

                if (status == "OK") {
                    @Suppress("UNCHECKED_CAST")
                    val results = respuesta["results"] as? List<Map<String, Any>>
                    if (!results.isNullOrEmpty()) {
                        val geometry = results[0]["geometry"] as? Map<String, Any>
                        val location = geometry?.get("location") as? Map<String, Any>
                        if (location != null) {
                            val lat = (location["lat"] as Number).toDouble()
                            val lng = (location["lng"] as Number).toDouble()
                            logger.info("CP $codigoPostal geocodificado vía Google: ($lat, $lng)")
                            cache[codigoPostal] = Pair(lat, lng)
                            return Pair(lat, lng)
                        }
                    }
                } else {
                    logger.warn("Geocoding API falló para CP $codigoPostal. Status: $status")
                }
            } catch (e: Exception) {
                logger.error("Error al llamar a Geocoding API para CP $codigoPostal", e)
            }
        } else {
            logger.warn("No se ha configurado GOOGLE_MAPS_API_KEY en el backend")
        }

        // 3. Fallback: centro de CDMX
        logger.warn("CP $codigoPostal no encontrado, usando coordenadas de fallback (CDMX)")
        return Pair(19.4326, -99.1332)
    }
}