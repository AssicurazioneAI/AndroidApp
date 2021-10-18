package com.mmk.data.repository

import android.util.Base64
import com.mmk.data.source.remote.image.ImageRemoteDataSource
import com.mmk.domain.model.Result
import com.mmk.domain.model.error.ErrorEntity
import com.mmk.domain.repository.BaseRepository
import com.mmk.domain.repository.image.ImageRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class ImageRepositoryImpl(
    private val imageRemoteDataSource: ImageRemoteDataSource,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) :
    ImageRepository, BaseRepository(dispatcher) {

    override suspend fun sendImage(imagePath: String): Result<Unit> = executeInBackground {
        val base64String=encodeBase64(imagePath)
        if (base64String==null) Result.Error(ErrorEntity.ImageError.WrongFormat)
        else imageRemoteDataSource.sendImage(base64String)
    }

    override fun encodeBase64(image:String):String?{
        return Base64.encodeToString(image.toByteArray(),Base64.NO_WRAP)
    }

}