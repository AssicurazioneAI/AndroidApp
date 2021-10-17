package com.mmk.domain.usecase.image

import com.mmk.domain.model.Result
import com.mmk.domain.model.error.ErrorEntity
import com.mmk.domain.repository.image.ImageRepository

class SendingImageUseCaseImpl(private val repository: ImageRepository) : SendingImageUseCase {
    override suspend fun invoke(base64Image: String?): Result<Unit> {
        return if (base64Image.isNullOrEmpty()) Result.Error(ErrorEntity.CommonError.EmptyOrNullData)
        else repository.sendImage(base64Image)
    }

}
