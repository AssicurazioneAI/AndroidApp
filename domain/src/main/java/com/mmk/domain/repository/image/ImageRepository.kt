package com.mmk.domain.repository.image

import com.mmk.domain.model.Result

interface ImageRepository {

    suspend fun sendImage(base64Image: String): Result<Unit>
}
