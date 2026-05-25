package com.kernel.crew.sys.adogta.dto.response

import com.kernel.crew.sys.adogta.entities.AnimalEntity

data class AnimalListItemResponse(
    val idAnimal: Int,
    val idPublicacion: Int,
    val idUsuario: Int,
    val nombre: String,
    val tipo: String,
    val edad: Int,
    val descripcion: String,
    val codigoPostal: String,
    val estadoVacunacion: String,
    val esterilizado: Boolean,
    val entrenado: Boolean,
    val razaNombre: String,
    val razaTalla: Int,
    val nivelEnergia: Int,
    val independencia: Int,
    val sociableNiños: Int,
    val sociableMascotas: Int,
    val fotos: List<String>,
    val padecimientos: List<String>,
    val estadoPublicacion: String? = null
) {
    companion object {
        fun from(animal: AnimalEntity): AnimalListItemResponse = AnimalListItemResponse(
            idAnimal = animal.idAnimal,
            idPublicacion = animal.idPublicacion,
            idUsuario = animal.idUsuario,
            nombre = animal.nombre,
            tipo = animal.tipo,
            edad = animal.edad,
            descripcion = animal.descripcion,
            codigoPostal = animal.codigoPostal,
            estadoVacunacion = animal.estadoVacunacion,
            esterilizado = animal.esterilizado,
            entrenado = animal.entrenado,
            razaNombre = animal.raza.nombre,
            razaTalla = animal.raza.talla,
            nivelEnergia = animal.overrideEnergia ?: animal.raza.nivelEnergia,
            independencia = animal.overrideIndependencia ?: animal.raza.independencia,
            sociableNiños = animal.overrideSociableNiños ?: animal.raza.sociableNiños,
            sociableMascotas = animal.overrideSociableMascotas ?: animal.raza.sociableMascotas,
            fotos = animal.fotos.toList(),
            padecimientos = animal.padecimientos.toList(),
            estadoPublicacion =animal.publicacion.estado
        )
    }
}
