package com.mmk.data.repository

import com.mmk.data.source.remote.car.CarRemoteDataSource
import com.mmk.data.util.ImageHelper
import com.mmk.domain.model.CarDamage
import com.mmk.domain.model.Result
import com.mmk.domain.model.error.ErrorEntity
import com.mmk.domain.repository.cardamage.CarRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class CarRepositoryImpl(
    private val carRemoteDataSource: CarRemoteDataSource,
    private val imageHelper: ImageHelper,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) :
    CarRepository, BaseRepository(dispatcher) {

    override suspend fun getCarDamage(imagePath: String): Result<CarDamage> = executeInBackground {
        val base64String = imageHelper.getBase64StringFromImagePath(imagePath)
        if (base64String == null) Result.Error(ErrorEntity.ImageError.WrongFormat)
        else {
            val response = carRemoteDataSource.getDamageResult(base64String)
            when (response) {
                is Result.Error -> response
                is Result.Success -> Result.Success(response.data.mapToDomainModel())
            }
        }
    }


}