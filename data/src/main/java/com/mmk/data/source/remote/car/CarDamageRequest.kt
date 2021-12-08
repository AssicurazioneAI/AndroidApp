package com.mmk.data.source.remote.car

import com.squareup.moshi.Json

data class CarDamageRequest(
    @Json(name = "image")
    val base64Image: String
)