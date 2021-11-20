package com.mmk.data.source.remote.car

import com.squareup.moshi.Json

data class CarDamageRequest(
    //TODO change json name
    @Json(name = "image")
    val base64Image: String
)