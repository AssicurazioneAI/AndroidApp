package com.mmk.data.repository

import com.mmk.data.source.remote.image.ImageRemoteDataSource
import com.mmk.data.util.ImageHelper
import com.mmk.domain.model.Result
import com.mmk.domain.model.error.ErrorEntity
import com.mmk.domain.repository.image.ImageRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class ImageRepositoryImpl(
    private val imageRemoteDataSource: ImageRemoteDataSource,
    private val imageHelper: ImageHelper,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) :
    ImageRepository, BaseRepository(dispatcher) {

    override suspend fun sendImage(imagePath: String): Result<Unit> = executeInBackground {
        val base64String = imageHelper.getBase64StringFromImagePath(imagePath)
        if (base64String == null) Result.Error(ErrorEntity.ImageError.WrongFormat)
        else imageRemoteDataSource.sendImage(base64String)
    }


}