package com.mmk.data.repository.image

import com.google.common.truth.Truth.assertThat
import com.mmk.data.repository.ImageRepositoryImpl
import com.mmk.data.source.remote.image.ImageRemoteDataSource
import com.mmk.data.util.ImageHelper
import com.mmk.domain.model.Result
import com.mmk.domain.model.error.ErrorEntity
import com.mmk.domain.repository.image.ImageRepository
import com.mmk.domain.util.MainCoroutineRule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest

import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ImageRepositoryImplTest {

    /**
     * 1) If image is sent successfully, it should return Success
     * 2) If sending image failed, it should return Error state
     * 3) If image is not found for given path, it should return Error state with NotFound
     * 4) If image couldn't convert to Base64 String before sending to the server, it should return
     *    WrongFormat Error State
     */

    private lateinit var imageRepository: ImageRepository
    private lateinit var imageRemoteDataSource: ImageRemoteDataSource
    private lateinit var imageHelper: ImageHelper

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        imageRemoteDataSource = mockk()
        imageHelper = mockk()
        imageRepository = ImageRepositoryImpl(imageRemoteDataSource, imageHelper,mainCoroutineRule.dispatcher)

    }

    @Test
    fun `sending image should return success when it is sent successfully to the server`() =
        mainCoroutineRule.runBlockingTest {
            val image = "imageSrc"
            val base64String = "Base64String"
            coEvery { imageHelper.getBase64StringFromImagePath(any())} returns base64String
            coEvery { imageRemoteDataSource.sendImage(base64String) } returns Result.Success(Unit)
            val response = imageRepository.sendImage(image)
            assertThat(response).isInstanceOf(Result.Success::class.java)
        }

    @Test
    fun `sending image should return error when sending failed from server side`() =
        mainCoroutineRule.runBlockingTest {
            val image = "imageSrc"
            val base64String = "Base64String"
            coEvery { imageHelper.getBase64StringFromImagePath(any())} returns base64String
            coEvery { imageRemoteDataSource.sendImage(any()) } returns Result.Error()
            val response = imageRepository.sendImage(image)
            assertThat(response).isInstanceOf(Result.Error::class.java)
        }

    @Test()
    fun `sending image should return unknown Error when server throw exception`() =
        mainCoroutineRule.runBlockingTest {
            val base64Image = "Base64Image"
            coEvery { imageHelper.getBase64StringFromImagePath(any())} returns base64Image
            coEvery { imageRemoteDataSource.sendImage(base64Image) } throws Exception()
            val response = imageRepository.sendImage("imagePath")
            assertThat(response).isInstanceOf(Result.Error::class.java)
            response as Result.Error
            assertThat(response.errorEntity).isInstanceOf(ErrorEntity.CommonError.Unknown::class.java)

        }

    @Test()
    fun `sendImage should verify image is in Base64 format before sending to the server`() =
        mainCoroutineRule.runBlockingTest {
            val image = "imagePath"
            val base64String = "Base64String"
            coEvery { imageHelper.getBase64StringFromImagePath(any())} returns base64String
            val response = imageRepository.sendImage(image)
            coVerify { imageRemoteDataSource.sendImage(base64String) }
        }

    @Test()
    fun `sendImage should return Error when image is not Base64String format`() =
        mainCoroutineRule.runBlockingTest {
            val image = "imagePath"
            coEvery { imageHelper.getBase64StringFromImagePath(any())} returns null
            val response = imageRepository.sendImage(image)
            assertThat(response).isInstanceOf(Result.Error::class.java)
            response as Result.Error
            assertThat(response.errorEntity).isInstanceOf(ErrorEntity.ImageError.WrongFormat::class.java)
        }

}