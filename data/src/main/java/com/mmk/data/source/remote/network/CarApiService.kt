package com.mmk.data.source.remote.network

import com.mmk.data.source.remote.car.CarDamageRequest
import com.mmk.data.source.remote.car.CarDamageResponse
import retrofit2.Response
import retrofit2.http.*

interface CarApiService {

    @GET(EndPoints.Car.GET_DAMAGE)
    suspend fun getCarDamageResult(@Body carDamageRequest: CarDamageRequest): CarDamageResponse

}