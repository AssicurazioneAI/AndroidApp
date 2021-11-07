package com.mmk.data.source.remote.image

import com.mmk.domain.model.Result

interface ImageRemoteDataSource {
    suspend fun sendImage(base64Image: String): Result<Unit>

}
