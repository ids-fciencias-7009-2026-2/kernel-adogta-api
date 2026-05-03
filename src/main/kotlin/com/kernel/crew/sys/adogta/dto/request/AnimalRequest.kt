package com.kernel.crew.sys.adogta.dto.request

data class AnimalRequest(
    val nombre: String,
    val especie: String,
    val edad: String,
    val tamano: String,
    val descripcion: String,
    val fotoUrl: String?,
    val usuarioId: Long
)