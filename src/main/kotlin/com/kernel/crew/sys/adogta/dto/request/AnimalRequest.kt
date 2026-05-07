package com.kernel.crew.sys.adogta.dto.request

import jakarta.validation.constraints.AssertTrue
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size

data class AnimalRequest(
    @field:NotBlank(message = "El nombre del animal es obligatorio.")
    @field:Size(max = 100, message = "El nombre no puede exceder 100 caracteres.")
    val nombre: String,

    @field:NotBlank(message = "El tipo es obligatorio.")
    @field:Pattern(regexp = "^(Perro|Gato)$", message = "El tipo debe ser 'Perro' o 'Gato'.")
    val tipo: String,

    @field:NotNull(message = "La edad es obligatoria.")
    @field:PositiveOrZero(message = "La edad no puede ser negativa.")
    @field:Max(value = 360, message = "La edad (en meses) no puede exceder 360.")
    val edad: Int,

    @field:NotNull(message = "La descripción es obligatoria.")
    @field:Size(max = 2000, message = "La descripción no puede exceder 2000 caracteres.")
    val descripcion: String,

    @field:NotBlank(message = "El código postal es obligatorio.")
    @field:Pattern(regexp = "^[0-9]{5}$", message = "El código postal debe tener exactamente 5 dígitos.")
    val codigoPostal: String,

    @field:NotBlank(message = "El estado de vacunación es obligatorio.")
    @field:Pattern(
        regexp = "^(Completo|Parcial)$",
        message = "El estado de vacunación debe ser 'Completo' o 'Parcial'."
    )
    val estadoVacunacion: String,

    @field:NotNull(message = "Indica el estado de esterilización del animal.")
    @field:AssertTrue(message = "Solo se aceptan animales esterilizados.")
    val esterilizado: Boolean?,
    val entrenado: Boolean,

    @field:Min(value = 1, message = "El id de la raza debe ser positivo.")
    val idRaza: Int,

    @field:Min(1) @field:Max(5)
    val overrideEnergia: Int? = null,

    @field:Min(1) @field:Max(5)
    val overrideIndependencia: Int? = null,

    @field:Min(1) @field:Max(5)
    val overrideSociableNiños: Int? = null,

    @field:Min(1) @field:Max(5)
    val overrideSociableMascotas: Int? = null,

    @field:Size(max = 20, message = "No puedes registrar más de 20 padecimientos.")
    val padecimientos: List<String> = emptyList(),

    @field:Size(max = 10, message = "No puedes registrar más de 10 fotos.")
    val fotos: List<String> = emptyList()
)
