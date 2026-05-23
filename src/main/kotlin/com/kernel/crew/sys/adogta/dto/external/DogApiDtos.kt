package com.kernel.crew.sys.adogta.dto.external

data class DogBreedSearchResponse(
    val name: String,
    val temperament: String? = null,
    val weight: DogWeight? = null
)

data class DogWeight(
    val metric: String? = null
)
