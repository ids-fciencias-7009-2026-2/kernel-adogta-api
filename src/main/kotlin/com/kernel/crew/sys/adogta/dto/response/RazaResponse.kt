package com.kernel.crew.sys.adogta.dto.response

import com.kernel.crew.sys.adogta.entities.RazaEntity

data class RazaResponse(
    val idRaza: Int,
    val nombre: String,
    val talla: Int,
    val independencia: Int,
    val nivelEnergia: Int,
    val personalidad: String,
    val sociableNiños: Int,
    val sociableMascotas: Int,
    val esHipoalergenico: Int
) {
    companion object {
        fun from(raza: RazaEntity): RazaResponse = RazaResponse(
            idRaza = raza.id ?: 0,
            nombre = raza.nombre,
            talla = raza.talla,
            independencia = raza.independencia,
            nivelEnergia = raza.nivelEnergia,
            personalidad = raza.personalidad,
            sociableNiños = raza.sociableNiños,
            sociableMascotas = raza.sociableMascotas,
            esHipoalergenico = raza.esHipoalergenico
        )
    }
}
