package com.mmk.data.source.remote.network

import com.mmk.data.source.remote.car.CarDamageRequest
import com.mmk.data.source.remote.car.CarDamageResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Url

interface CarApiService {

    @POST(EndPoints.Car.GET_DAMAGE)
    suspend fun getCarDamageResult(@Body carDamageRequest: CarDamageRequest): CarDamageResponse

}