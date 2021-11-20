package com.mmk.domain.usecase.cardamage

import com.mmk.domain.model.CarDamage
import com.mmk.domain.model.Result
import com.mmk.domain.model.error.ErrorEntity
import com.mmk.domain.repository.cardamage.CarRepository

class GettingCarDamageUseCaseImpl(private val repository: CarRepository) :
    GettingCarDamageUseCase {
    override suspend fun invoke(imagePath: String?): Result<CarDamage> {
        return if (imagePath.isNullOrEmpty()) Result.Error(ErrorEntity.CommonError.EmptyOrNullData)
        else repository.getCarDamage(imagePath)
    }

}
