package com.kernel.crew.sys.adogta.dto.external

import com.fasterxml.jackson.annotation.JsonProperty

data class CatBreedSearchResponse(
    val name: String,
    val temperament: String? = null,
    val weight: CatWeight? = null,
    @JsonProperty("energy_level")
    val energyLevel: Int? = null,
    val independence: Int? = null,
    @JsonProperty("child_friendly")
    val childFriendly: Int? = null,
    @JsonProperty("dog_friendly")
    val dogFriendly: Int? = null,
    val hypoallergenic: Int? = null
)

data class CatWeight(
    val metric: String? = null
)
