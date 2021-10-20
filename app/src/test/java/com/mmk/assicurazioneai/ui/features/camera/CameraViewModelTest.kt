package com.mmk.assicurazioneai.ui.features.camera

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.mmk.assicurazioneai.ui.base.ErrorState
import com.mmk.assicurazioneai.ui.base.UiState
import com.mmk.assicurazioneai.util.MainCoroutineRule
import com.mmk.assicurazioneai.util.getOrAwaitValue
import com.mmk.domain.model.Result
import com.mmk.domain.model.error.ErrorEntity
import com.mmk.domain.usecase.image.SendingImageUseCase
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

    private lateinit var sendingImageUseCase: SendingImageUseCase

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Set the main coroutines dispatcher for unit testing.
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        sendingImageUseCase = mockk()
        cameraViewModel = CameraViewModel(sendingImageUseCase)
    }

    @Test(expected = TimeoutException::class)
    fun `verify ui state is not set when initialize viewModel`() {
        val currentUiState = cameraViewModel.sendingImageUiState
        currentUiState.getOrAwaitValue(time = 1)
    }

    @Test
    fun `verify UI is in loading state when sending image for given correct imagePath`()=mainCoroutineRule.runBlockingTest {
        val uiState = cameraViewModel.sendingImageUiState
        coEvery { sendingImageUseCase(any()) } coAnswers  {
            delay(1000)
            Result.Success(Unit)
        }
        val imagePath: String = "imagePath"
        cameraViewModel.sendImage(imagePath)
        assertThat(uiState.getOrAwaitValue()).isInstanceOf(UiState.Loading::class.java)
    }

    @Test
    fun `verify UI is in Success state when sending image is successful`() {
        val uiState = cameraViewModel.sendingImageUiState
        val imagePath: String = "imagePath"
        coEvery { sendingImageUseCase(imagePath) } returns Result.Success(Unit)
        cameraViewModel.sendImage(imagePath)
        assertThat(uiState.getOrAwaitValue()).isInstanceOf(UiState.Success::class.java)
    }

    @Test
    fun `verify UI is in Error state when sending image is failed`() {
        val uiState = cameraViewModel.sendingImageUiState
        val imagePath: String = "imagePath"
        coEvery { sendingImageUseCase(imagePath) } returns Result.Error()
        cameraViewModel.sendImage(imagePath)
        assertThat(uiState.getOrAwaitValue()).isInstanceOf(UiState.Error::class.java)
    }

    @Test
    fun `verify UI is notified when sending image is successful`() {
        val onImageSent = cameraViewModel.onImageSent
        val imagePath: String = "imagePath"
        coEvery { sendingImageUseCase(imagePath) } returns Result.Success(Unit)
        cameraViewModel.sendImage(imagePath)
        assertThat(onImageSent.getOrAwaitValue().peekContent()).isNotNull()
    }

    @Test
    fun `verify errorState is not null when sending image fails`() {
        val errorEntity = ErrorEntity.CommonError.Unknown
        coEvery { sendingImageUseCase(any()) } returns Result.Error(errorEntity)
        cameraViewModel.sendImage(null)
        val errorStateValue = cameraViewModel.errorState.getOrAwaitValue()
        assertThat(errorStateValue).isNotNull()
    }

    @Test
    fun `verify errorState message is server error message when sending image fails with ApiError`() {
        val errorMessage = "Image is not a selfie"
        val errorEntity = ErrorEntity.ApiError.Other(errorMessage)
        coEvery { sendingImageUseCase(any()) } returns Result.Error(errorEntity)

        cameraViewModel.sendImage(null)

        val errorStateValue = cameraViewModel.errorState.getOrAwaitValue().peekContent()
        assertThat(errorStateValue).isNotNull()
        assertThat(errorStateValue).isInstanceOf(ErrorState.Message::class.java)
        errorStateValue as ErrorState.Message
        assertThat(errorStateValue.message).isEqualTo(errorMessage)

    }


}