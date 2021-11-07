package com.mmk.data.source.remote.image

import com.mmk.domain.model.Result
import kotlinx.coroutines.delay

class FakeImageRemoteDataSource : ImageRemoteDataSource {

    override suspend fun sendImage(base64Image: String): Result<Unit> {
        delay(2000)
        return Result.Success(Unit)
    }

}