package com.mmk.domain.repository.image

import com.mmk.domain.model.Result
import java.util.*

interface ImageRepository {

    suspend fun sendImage(imagePath: String): Result<Unit>
    fun encodeBase64(image: String): String?

}
