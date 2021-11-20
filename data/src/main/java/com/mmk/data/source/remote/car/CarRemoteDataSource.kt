package com.mmk.data.source.remote.car

import com.mmk.domain.model.Result

interface CarRemoteDataSource {
    suspend fun getDamageResult(base64Image: String): Result<CarDamageResponse>

}
