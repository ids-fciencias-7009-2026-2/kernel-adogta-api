package com.kernel.crew.sys.adogta.servicies

import com.kernel.crew.sys.adogta.domain.TemperamentMapper
import com.kernel.crew.sys.adogta.dto.external.CatBreedSearchResponse
import com.kernel.crew.sys.adogta.dto.external.DogBreedSearchResponse
import com.kernel.crew.sys.adogta.dto.request.RazaCreateRequest
import com.kernel.crew.sys.adogta.dto.response.RazaResponse
import com.kernel.crew.sys.adogta.entities.RazaEntity
import com.kernel.crew.sys.adogta.repositories.RazaRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class RazaSyncService(
    private val razaRepository: RazaRepository,
    @Value("\${app.thedogapi.base-url:https://api.thedogapi.com/v1}")
    private val dogBaseUrl: String,
    @Value("\${app.thecatapi.base-url:https://api.thecatapi.com/v1}")
    private val catBaseUrl: String,
    @Value("\${app.thedogapi.key:}")
    private val dogApiKey: String,
    @Value("\${app.thecatapi.key:}")
    private val catApiKey: String
) {
    private val logger = LoggerFactory.getLogger(RazaSyncService::class.java)
    private val restTemplate = RestTemplate()

    fun crearRaza(request: RazaCreateRequest): RazaResponse {
        val tipoEntrada = request.tipo.lowercase()
        if (tipoEntrada != "perro" && tipoEntrada != "gato") {
            logger.warn("Tipo de raza inválido: {}", request.tipo)
            return RazaResponse.error(MENSAJE_NO_EXISTE)
        }

        val nombreNormalizado = normalizar(request.nombre)
        if (nombreNormalizado.length < 3) {
            logger.warn("Nombre de raza inválido (len<3): {}", request.nombre)
            return RazaResponse.error(MENSAJE_NO_EXISTE)
        }

        val tipoBd = if (tipoEntrada == "perro") "Perro" else "Gato"

        val existentes = razaRepository.findAllByTipoIgnoreCase(tipoBd)
        val razaExistente = existentes.firstOrNull {
            normalizar(it.nombre) == nombreNormalizado
        }
        if (razaExistente != null) return RazaResponse.from(razaExistente)

        val razaNueva = if (tipoEntrada == "perro") {
            obtenerRazaPerro(nombreNormalizado, tipoBd)
        } else {
            obtenerRazaGato(nombreNormalizado, tipoBd)
        }

        if (razaNueva == null) {
            logger.warn("Raza no encontrada para tipo={} nombre={}", tipoEntrada, nombreNormalizado)
            return RazaResponse.error(MENSAJE_NO_EXISTE)
        }

        val guardada = razaRepository.save(razaNueva)
        return RazaResponse.from(guardada)
    }

    private fun obtenerRazaPerro(nombreNormalizado: String, tipoBd: String): RazaEntity? {
        val resultados = buscarRazasPerro(nombreNormalizado)
        val match = resultados.firstOrNull { normalizar(it.name) == normalizar(nombreNormalizado) } ?: return null

        val temperament = match.temperament ?: ""
        val mapped = TemperamentMapper.mapTemperament(temperament)
        val talla = match.weight?.metric?.let { TemperamentMapper.mapWeightToTalla(it) } ?: 3
        val hipoalergenico = if (TemperamentMapper.HYPOALLERGENIC_DOG_BREEDS.contains(normalizar(match.name))) 1 else 0

        return RazaEntity(
            nombre = match.name,
            tipo = tipoBd,
            talla = talla,
            independencia = mapped.independencia,
            nivelEnergia = mapped.nivelEnergia,
            personalidad = "",
            sociableNiños = mapped.sociableNinos,
            sociableMascotas = mapped.sociableMascotas,
            esHipoalergenico = hipoalergenico
        )
    }

    private fun obtenerRazaGato(nombre: String, tipoBd: String): RazaEntity? {
        val resultados = buscarRazasGato(nombre)
        val match = resultados.firstOrNull { normalizar(it.name) == normalizar(nombre) } ?: return null

        val talla = match.weight?.metric?.let { TemperamentMapper.mapWeightToTalla(it) } ?: 3
        val energia = match.energyLevel ?: 3
        val independencia = match.independence ?: 3
        val sociableNinos = match.childFriendly ?: 3
        val sociableMascotas = match.dogFriendly ?: 3
        val hipoalergenico = match.hypoallergenic ?: 0

        return RazaEntity(
            nombre = match.name,
            tipo = tipoBd,
            talla = talla,
            independencia = independencia.coerceIn(1, 5),
            nivelEnergia = energia.coerceIn(1, 5),
            personalidad = "",
            sociableNiños = sociableNinos.coerceIn(1, 5),
            sociableMascotas = sociableMascotas.coerceIn(1, 5),
            esHipoalergenico = if (hipoalergenico >= 1) 1 else 0
        )
    }

    private fun buscarRazasPerro(nombreNormalizado: String): List<DogBreedSearchResponse> {
        return try {
            val uri = UriComponentsBuilder.fromUriString("$dogBaseUrl/breeds/search")
                .queryParam("q", nombreNormalizado)
                .build()
                .encode()
                .toUri()
            val response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                HttpEntity<Void>(buildHeaders(dogApiKey)),
                object : ParameterizedTypeReference<List<DogBreedSearchResponse>>() {}
            )
            response.body ?: emptyList()
        } catch (ex: Exception) {
            logger.warn("Error consultando The Dog API: {}", ex.message)
            emptyList()
        }
    }

    private fun buscarRazasGato(nombreNormalizado: String): List<CatBreedSearchResponse> {
        return try {
            val uri = UriComponentsBuilder.fromUriString("$catBaseUrl/breeds/search")
                .queryParam("q", nombreNormalizado)
                .build()
                .encode()
                .toUri()
            val response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                HttpEntity<Void>(buildHeaders(catApiKey)),
                object : ParameterizedTypeReference<List<CatBreedSearchResponse>>() {}
            )
            response.body ?: emptyList()
        } catch (ex: Exception) {
            logger.warn("Error consultando The Cat API: {}", ex.message)
            emptyList()
        }
    }

    private fun buildHeaders(apiKey: String): HttpHeaders {
        val headers = HttpHeaders()
        if (apiKey.isNotBlank()) headers.set("x-api-key", apiKey)
        return headers
    }

    private fun normalizar(nombre: String): String {
        return nombre
            .lowercase()
            .replace(Regex("[^a-z ]"), "")
            .replace(Regex("\\s+"), " ")
            .trim()
    }

    companion object {
        private const val MENSAJE_NO_EXISTE = "La raza que buscas no existe"
    }
}
