package com.mmk.domain.usecase.image

import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.mmk.domain.model.Result
import com.mmk.domain.repository.image.ImageRepository
import com.mmk.domain.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

@ExperimentalCoroutinesApi
class SendingImageUseCaseTest {

    /**
     * 1)Image should not be empty, when it is empty or null should return error
     * 2)When sending image it should return given response (fail when it is failed, or success)
     */

    private lateinit var sendingImageUseCase: SendingImageUseCase
    private lateinit var imageRepository: ImageRepository

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    @Before
    fun setUp() {
        sendingImageUseCase = SendingImageUseCase(imageRepository)
    }

    @Test
    fun `sending image should return error when sending null image`() =
        mainCoroutineRule.runBlockingTest {
            val image: String? = null
            val response: Result<Unit> = sendingImageUseCase.send(image)
            assertThat(response).isInstanceOf(Result.Error::class.java)
        }

    @Test
    fun `sending image should return error when sending empty image`() =
        mainCoroutineRule.runBlockingTest {
            val image: String = ""
            val response: Result<Unit> = sendingImageUseCase.send(image)
            assertThat(response).isInstanceOf(Result.Error::class.java)
        }

    @Test
    fun `sending image should return success when image is sent successfully`() =
        mainCoroutineRule.runBlockingTest {
            val image: String = "Base64String"
            val response: Result<Unit> = sendingImageUseCase.send(image)
            assertThat(response).isInstanceOf(Result.Success::class.java)
        }

    @Test
    fun `sending image should return fail when sending image is failed`() =
        mainCoroutineRule.runBlockingTest {
            val image: String = "Base64String"
            val response: Result<Unit> = sendingImageUseCase.send(image)
            assertThat(response).isInstanceOf(Result.Error::class.java)
        }
}