package com.mmk.domain.usecase.cardamage

import com.mmk.domain.model.CarDamage
import com.mmk.domain.model.Result

interface GettingCarDamageUseCase {
    suspend operator fun invoke(imagePath: String?): Result<CarDamage>
}