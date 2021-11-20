package com.mmk.domain.usecase.cardamage

import com.google.common.truth.Truth.assertThat
import com.mmk.domain.model.CarDamage
import com.mmk.domain.model.Result
import com.mmk.domain.model.error.ErrorEntity
import com.mmk.domain.repository.cardamage.CarRepository
import com.mmk.domain.util.MainCoroutineRule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GettingCarDamageUseCaseTest {

    /**
     * 1)Image should not be empty, when it is empty or null should return EmptyOrNull Error
     * 2)When sending image it should return given response (fail when it is failed, or success)
     * 3)When sending image, if it fails from server it should return ApiError with message
     */

    private lateinit var gettingCarDamageUseCase: GettingCarDamageUseCase
    private lateinit var carRepository: CarRepository

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    @Before
    fun setUp() {
        carRepository = mockk()
        gettingCarDamageUseCase = GettingCarDamageUseCaseImpl(carRepository)
    }

    @Test
    fun `sending image should return EmptyData Error when sending null image`() =
        mainCoroutineRule.runBlockingTest {
            val image: String? = null
            val response: Result<CarDamage> = gettingCarDamageUseCase(image)
            assertThat(response).isInstanceOf(Result.Error::class.java)
            assertThat((response as Result.Error).errorEntity).isInstanceOf(ErrorEntity.CommonError.EmptyOrNullData::class.java)

        }

    @Test
    fun `sending image should return EmptyData error when sending empty image`() =
        mainCoroutineRule.runBlockingTest {
            val image: String = ""
            val response: Result<CarDamage> = gettingCarDamageUseCase(image)
            assertThat(response).isInstanceOf(Result.Error::class.java)
            assertThat((response as Result.Error).errorEntity).isInstanceOf(ErrorEntity.CommonError.EmptyOrNullData::class.java)
        }

    @Test
    fun `sending image should not call server function when it is empty or null`() =
        mainCoroutineRule.runBlockingTest {
            val image = null
            val response: Result<CarDamage> = gettingCarDamageUseCase(image)
            coVerify(exactly = 0) { carRepository.getCarDamage(any()) }
        }

    @Test
    fun `sending image should return success when image is sent successfully`() =
        mainCoroutineRule.runBlockingTest {
            val image: String = "imagePath"

            coEvery { carRepository.getCarDamage(any()) } returns Result.Success(CarDamage())
            val response: Result<CarDamage> = gettingCarDamageUseCase(image)
            assertThat(response).isInstanceOf(Result.Success::class.java)
        }

    @Test
    fun `sending image should return ApiError when sending image is failed`() =
        mainCoroutineRule.runBlockingTest {
            val image: String = "imagePath"
            val errorMessage = "Error occurred while sending an image"
            coEvery { carRepository.getCarDamage(any()) } returns Result.Error(
                errorEntity = ErrorEntity.ApiError.Other(
                    errorMessage
                )
            )
            val response: Result<CarDamage> = gettingCarDamageUseCase(image)
            assertThat(response).isInstanceOf(Result.Error::class.java)
            assertThat((response as Result.Error).errorEntity).isEqualTo(
                ErrorEntity.ApiError.Other(
                    errorMessage
                )
            )
        }
}