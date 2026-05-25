package com.kernel.crew.sys.adogta.dto.response

/**
 * DTO que representa un animal en el mapa de publicaciones activas.
 * Incluye las coordenadas aproximadas derivadas del código postal.
 */
data class AnimalMapaResponse(

    /** ID de la publicación */
    val idPublicacion: Int,

    /** ID del animal */
    val idAnimal: Int,

    /** Nombre del animal */
    val nombre: String,

    /** Tipo: "Perro" o "Gato" */
    val tipo: String,

    /** Nombre de la raza */
    val razaNombre: String,

    /** URL de la foto principal del animal (null si no tiene fotos) */
    val fotoPrincipal: String?,

    /** Código postal del animal */
    val codigoPostal: String,

    /** Latitud aproximada derivada del código postal */
    val lat: Double,

    /** Longitud aproximada derivada del código postal */
    val lng: Double
)