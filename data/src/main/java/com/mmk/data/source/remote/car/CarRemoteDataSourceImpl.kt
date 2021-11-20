package com.mmk.data.source.remote.car

import com.mmk.data.source.remote.network.CarApiService
import com.mmk.domain.model.Result
import com.mmk.domain.model.error.ErrorEntity
import kotlinx.coroutines.delay

class CarRemoteDataSourceImpl(private val carApiService: CarApiService) : CarRemoteDataSource {

    override suspend fun getDamageResult(base64Image: String): Result<CarDamageResponse> {
        return try {
            val carDamageRequest = CarDamageRequest(base64Image = base64Image)
            val response = carApiService.getCarDamageResult(carDamageRequest)
            Result.Success(response)

        } catch (e: Exception) {
            Result.Error(ErrorEntity.ApiError.ServerProblem(e))
        }
    }
}