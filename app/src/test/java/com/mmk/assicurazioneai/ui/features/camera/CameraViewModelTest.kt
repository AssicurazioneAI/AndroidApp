package com.mmk.assicurazioneai.ui.features.camera

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.mmk.assicurazioneai.ui.base.ErrorMessage
import com.mmk.assicurazioneai.ui.base.UiState
import com.mmk.assicurazioneai.util.MainCoroutineRule
import com.mmk.assicurazioneai.util.getOrAwaitValue
import com.mmk.domain.model.CarDamage
import com.mmk.domain.model.Result
import com.mmk.domain.model.error.ErrorEntity
import com.mmk.domain.usecase.cardamage.GettingCarDamageUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeoutException

@ExperimentalCoroutinesApi
class CameraViewModelTest {

    private lateinit var cameraViewModel: CameraViewModel

    private lateinit var gettingCarDamageUseCase: GettingCarDamageUseCase


    private lateinit var imagePath: Uri

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Set the main coroutines dispatcher for unit testing.
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        imagePath = mockk()
        every { imagePath.toString() } returns "imagePath"
        gettingCarDamageUseCase = mockk()
        cameraViewModel = CameraViewModel(gettingCarDamageUseCase)
    }

    @Test(expected = TimeoutException::class)
    fun `verify ui state is not set when initialize viewModel`() {
        val currentUiState = cameraViewModel.sendingImageUiState
        currentUiState.getOrAwaitValue(time = 1)
    }

    @Test
    fun `verify UI is in loading state when sending image for given correct imagePath`() =
        mainCoroutineRule.runBlockingTest {
            val uiState = cameraViewModel.sendingImageUiState
            coEvery { gettingCarDamageUseCase(any()) } coAnswers {
                delay(1000)
                Result.Success(CarDamage())
            }
            cameraViewModel.setImagePath(imagePath)
            cameraViewModel.sendImage()
            assertThat(uiState.getOrAwaitValue()).isInstanceOf(UiState.Loading::class.java)
        }

    @Test
    fun `verify UI is in Success state when sending image is successful`() {
        val uiState = cameraViewModel.sendingImageUiState
        cameraViewModel.setImagePath(imagePath)
        val imagePathAsString = cameraViewModel.imagePath.getOrAwaitValue()
        coEvery { gettingCarDamageUseCase(imagePathAsString) } returns Result.Success(CarDamage())
        cameraViewModel.sendImage()
        assertThat(uiState.getOrAwaitValue()).isInstanceOf(UiState.Success::class.java)
    }

    @Test
    fun `verify UI is in Error state when sending image is failed`() {
        val uiState = cameraViewModel.sendingImageUiState
        cameraViewModel.setImagePath(imagePath)
        val imagePathAsString = cameraViewModel.imagePath.getOrAwaitValue()
        coEvery { gettingCarDamageUseCase(imagePathAsString) } returns Result.Error()
        cameraViewModel.sendImage()
        assertThat(uiState.getOrAwaitValue()).isInstanceOf(UiState.Error::class.java)
    }

    @Test
    fun `verify UI is notified when sending image is successful`() {
        val onImageSent = cameraViewModel.onImageSent
        cameraViewModel.setImagePath(imagePath)
        val imagePathAsString = cameraViewModel.imagePath.getOrAwaitValue()
        coEvery { gettingCarDamageUseCase(imagePathAsString) } returns Result.Success(CarDamage())
        cameraViewModel.sendImage()
        assertThat(onImageSent.getOrAwaitValue().peekContent()).isNotNull()
    }

    @Test
    fun `verify ErrorEntity is not null when sending image fails`() {
        val errorEntity = ErrorEntity.CommonError.Unknown()
        coEvery { gettingCarDamageUseCase(any()) } returns Result.Error(errorEntity)
        cameraViewModel.setImagePath(null)
        cameraViewModel.sendImage()
        val errorStateValue = cameraViewModel.errorEntity.getOrAwaitValue()
        assertThat(errorStateValue).isNotNull()
    }

    @Test
    fun `verify error message is server error message when sending image fails with ApiError`() {
        val errorMessage = "Image is not a selfie"
        val errorEntity = ErrorEntity.ApiError.Other(errorMessage)
        coEvery { gettingCarDamageUseCase(any()) } returns Result.Error(errorEntity)

        cameraViewModel.setImagePath(null)

        cameraViewModel.sendImage()

        val errorMessageValue = cameraViewModel.errorMessage.getOrAwaitValue().peekContent()
        assertThat(errorMessageValue).isNotNull()
        assertThat(errorMessageValue).isInstanceOf(ErrorMessage.Message::class.java)
        errorMessageValue as ErrorMessage.Message
        assertThat(errorMessageValue.message).isEqualTo(errorMessage)

    }


}