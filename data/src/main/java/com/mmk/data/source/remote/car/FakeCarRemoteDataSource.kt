package com.mmk.data.source.remote.car

import com.mmk.domain.model.Result
import kotlinx.coroutines.delay

class FakeCarRemoteDataSource : CarRemoteDataSource {

    override suspend fun getDamageResult(base64Image: String): Result<CarDamageResponse> {
        delay(2000)
        val carDamageResponse = CarDamageResponse.EMPTY
        return Result.Success(carDamageResponse)
    }

}