package com.kernel.crew.sys.adogta.servicies

import com.fasterxml.jackson.core.type.TypeReference
import com.kernel.crew.sys.adogta.dto.response.CatalogoResponse
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import jakarta.annotation.PostConstruct
import com.fasterxml.jackson.databind.ObjectMapper
import com.kernel.crew.sys.adogta.controllers.RazaController
import org.slf4j.LoggerFactory

@Service
class CatalogoRazasService() {

    private val logger = LoggerFactory.getLogger(RazaController::class.java)

    private val objectMapper = ObjectMapper()

    private lateinit var cacheRazas: List<CatalogoResponse>

    @PostConstruct
    fun inicializarCatalogo() {
        val resource = ClassPathResource("catalogo.json")
        
        cacheRazas = objectMapper.readValue(
            resource.inputStream, 
            object : TypeReference<List<CatalogoResponse>>() {}
        )
        println("Catálogo de razas cargado en memoria: ${cacheRazas.size} razas.")
    }

    fun buscarSugerencias(entradaUsuario: String): List<CatalogoResponse> {
        val busqueda = entradaUsuario.trim().lowercase()
        if (busqueda.isEmpty()) return emptyList()

        val coincidencias = cacheRazas.filter { 
            it.nombreEs.lowercase().contains(busqueda) || it.nombreEn.lowercase().contains(busqueda)
        }

        return coincidencias.sortedWith(
            compareBy<CatalogoResponse> { raza ->
                if (raza.nombreEs.lowercase().startsWith(busqueda)) 0 else 1
            }.thenBy { raza ->
                Math.abs(raza.nombreEs.length - busqueda.length)
            }
        ).take(5) 
    }
}