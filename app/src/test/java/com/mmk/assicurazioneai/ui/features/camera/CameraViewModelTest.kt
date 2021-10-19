package com.mmk.assicurazioneai.ui.features.camera

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.mmk.assicurazioneai.ui.base.UiState
import com.mmk.assicurazioneai.util.getOrAwaitValue
import com.mmk.domain.usecase.image.SendingImageUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeoutException

class CameraViewModelTest {

    private lateinit var cameraViewModel: CameraViewModel

    private lateinit var sendingImageUseCase: SendingImageUseCase

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

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
    fun `verify UI is in loading state when sending image for given correct imagePath`() {
        val uiState = cameraViewModel.sendingImageUiState
        val imagePath: String? = null
        cameraViewModel.sendImage(imagePath)
        assertThat(uiState.getOrAwaitValue()).isInstanceOf(UiState.Loading::class.java)
    }
}