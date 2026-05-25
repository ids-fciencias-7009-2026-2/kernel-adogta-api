package com.kernel.crew.sys.adogta.dto.response

import com.kernel.crew.sys.adogta.entities.RazaEntity

data class RazaResponse(
    val idRaza: Int? = null,
    val nombre: String? = null,
    val tipo: String? = null,
    val talla: Int? = null,
    val independencia: Int? = null,
    val nivelEnergia: Int? = null,
    val personalidad: String? = null,
    val sociableNiños: Int? = null,
    val sociableMascotas: Int? = null,
    val esHipoalergenico: Int? = null,
    val mensaje: String? = null
) {
    companion object {
        fun from(raza: RazaEntity): RazaResponse = RazaResponse(
            idRaza = raza.id ?: 0,
            nombre = raza.nombre,
            tipo = raza.tipo,
            talla = raza.talla,
            independencia = raza.independencia,
            nivelEnergia = raza.nivelEnergia,
            personalidad = raza.personalidad,
            sociableNiños = raza.sociableNiños,
            sociableMascotas = raza.sociableMascotas,
            esHipoalergenico = raza.esHipoalergenico
        )

        fun error(mensaje: String): RazaResponse = RazaResponse(
            mensaje = mensaje
        )
    }
}
