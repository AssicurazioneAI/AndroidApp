package com.mmk.data.source.remote.car

import com.mmk.data.mapper.DomainMapper
import com.mmk.domain.model.CarDamage
import com.squareup.moshi.Json

data class CarDamageResponse(
    //TODO change json name
    @Json(name = "image")
    val coordinates: String
) : DomainMapper<CarDamage> {
    companion object {
        //This Empty is created for testing purposes
        val EMPTY = CarDamageResponse("")
    }

    //TODO Implement this Model
    override fun mapToDomainModel(): CarDamage {
        return CarDamage()
    }
}