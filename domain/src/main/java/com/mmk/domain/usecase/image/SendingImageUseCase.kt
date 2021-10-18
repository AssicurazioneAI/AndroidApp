package com.mmk.domain.usecase.image

import com.mmk.domain.model.Result

interface SendingImageUseCase {
    suspend operator fun invoke(imagePath: String?): Result<Unit>
}