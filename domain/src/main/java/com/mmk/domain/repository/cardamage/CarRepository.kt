package com.mmk.domain.repository.cardamage

import com.mmk.domain.model.CarDamage
import com.mmk.domain.model.Result

interface CarRepository {

    suspend fun getCarDamage(imagePath: String): Result<CarDamage>
}
