package com.mmk.domain.usecase.image

import com.mmk.domain.model.Result
import com.mmk.domain.repository.BaseRepository
import com.mmk.domain.repository.image.ImageRepository

class SendingImageUseCase(private val repository: ImageRepository) {
    suspend fun send(base64Image: String?): Result<Unit> {
        if (base64Image.isNullOrEmpty()) return Result.Error()
        else return repository.sendImage(base64Image)
    }

}
