package com.kernel.crew.sys.adogta.controllers

import com.kernel.crew.sys.adogta.dto.response.RazaResponse
import com.kernel.crew.sys.adogta.repositories.RazaRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/razas")
@CrossOrigin(origins = ["http://localhost:5173"])
class RazaController(
    private val razaRepository: RazaRepository
) {

    @GetMapping
    fun listarRazas(): ResponseEntity<List<RazaResponse>> {
        val razas = razaRepository.findAll().map(RazaResponse::from)
        return ResponseEntity.ok(razas)
    }

    @GetMapping("/{id}")
    fun obtenerRaza(@PathVariable id: Int): ResponseEntity<Any> {
        val raza = razaRepository.findById(id).orElse(null)
            ?: return ResponseEntity.status(404)
                .body(mapOf("error" to "Raza no encontrada con id: $id"))
        return ResponseEntity.ok(RazaResponse.from(raza))
    }
}
