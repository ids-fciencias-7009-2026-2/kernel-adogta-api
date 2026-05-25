package com.kernel.crew.sys.adogta.dto.response

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * DTO que representa la respuesta con los rasgos mapeados.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class LlmMapResponse(
    @JsonProperty("nombre")
    @JsonAlias("name")
    val nombre: String,

    @JsonProperty("temperamento")
    @JsonAlias("temperament")
    val temperamento: String,

    @JsonProperty("nivel_energia")
    @JsonAlias("nivelEnergia")
    val nivelEnergia: Int,

    @JsonProperty("sociable_ninos")
    @JsonAlias("sociableNinos", "sociable_niños")
    val sociableNiños: Int,

    @JsonProperty("sociable_mascotas")
    @JsonAlias("sociableMascotas")
    val sociableMascotas: Int,

    @JsonProperty("independencia")
    @JsonAlias("independence")
    val independencia: Int
)