package com.ygs.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailResponse(
    @SerialName("image") val imageUrl: String,
    @SerialName("meta") val meta: String,
    @SerialName("name") val name: String,
    @SerialName("price") val price: Int,
    @SerialName("rating") val rating: String,
    @SerialName("synopsis") val synopsis: String
)