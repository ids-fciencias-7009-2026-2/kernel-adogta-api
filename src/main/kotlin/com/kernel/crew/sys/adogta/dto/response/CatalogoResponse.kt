package com.kernel.crew.sys.adogta.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class CatalogoResponse(
    @JsonProperty("nombre_en")
    val nombreEn: String,
    @JsonProperty("nombre_es")
    val nombreEs: String
)