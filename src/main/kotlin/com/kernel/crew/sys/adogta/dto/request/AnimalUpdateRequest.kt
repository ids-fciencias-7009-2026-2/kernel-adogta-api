package com.kernel.crew.sys.adogta.dto.request

import jakarta.validation.constraints.*

/**
 * DTO para la edición de un animal ya publicado.
 * Todos los campos son opcionales; solo se actualizan los que se envíen.
 *
 * Endpoint: PUT /api/animales/{idAnimal}
 */
data class AnimalUpdateRequest(

    @field:Size(max = 100)
    val nombre: String? = null,

    @field:Pattern(regexp = "^(Perro|Gato)$")
    val tipo: String? = null,

    @field:PositiveOrZero @field:Max(360)
    val edad: Int? = null,

    @field:Size(max = 2000)
    val descripcion: String? = null,

    @field:Pattern(regexp = "^[0-9]{5}$")
    val codigoPostal: String? = null,

    @field:Pattern(regexp = "^(Completo|Parcial)$")
    val estadoVacunacion: String? = null,

    val esterilizado: Boolean? = null,
    val entrenado: Boolean? = null,

    @field:Min(1)
    val idRaza: Int? = null,

    @field:Min(1) @field:Max(5) val overrideEnergia: Int? = null,
    @field:Min(1) @field:Max(5) val overrideIndependencia: Int? = null,
    @field:Min(1) @field:Max(5) val overrideSociableNiños: Int? = null,
    @field:Min(1) @field:Max(5) val overrideSociableMascotas: Int? = null,

    @field:Size(max = 20)
    val padecimientos: List<String>? = null,

    @field:Size(max = 10)
    val fotos: List<String>? = null
)