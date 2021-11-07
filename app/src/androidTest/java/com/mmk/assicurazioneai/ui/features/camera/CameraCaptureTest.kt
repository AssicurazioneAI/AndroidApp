package com.mmk.assicurazioneai.ui.features.camera

import android.net.Uri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.test.annotation.UiThreadTest

import com.google.common.truth.Truth.assertThat
import com.mmk.assicurazioneai.util.FakeActivityResultRegistry
import com.mmk.assicurazioneai.util.getOrAwaitValu
import com.mmk.assicurazioneai.utils.ImageUriCreator
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeoutException

class CameraCaptureTest {

    private lateinit var registry: FakeActivityResultRegistry<Uri>
    private lateinit var lifeCycle: LifecycleRegistry
    private lateinit var cameraCapture: CameraCapture
    private lateinit var imageUriCreator: ImageUriCreator


    @UiThreadTest
    @Before
    fun setUp() {
        val lifeCycleOwner: LifecycleOwner = mockk()
        lifeCycle = LifecycleRegistry(lifeCycleOwner)
        every { lifeCycleOwner.lifecycle } returns lifeCycle
        registry = FakeActivityResultRegistry()
        imageUriCreator = mockk()
        cameraCapture = CameraCapture(registry, imageUriCreator)
        lifeCycle.addObserver(cameraCapture)
        lifeCycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

    }

    @UiThreadTest
    @Test
    fun verify_imageIsCaptured_whenEverythingIsOkay() {
        lifeCycle.currentState = Lifecycle.State.STARTED
        val expectedResult = Uri.parse("imagePath")
        every { imageUriCreator.createAndGetNewImageUri() } returns expectedResult
        registry.expectedResult = expectedResult
        registry.isResultFailed = false

        cameraCapture.captureImage()
        val createdImageUri = cameraCapture.capturedImageUri.getOrAwaitValu()

        assertThat(createdImageUri).isEqualTo(expectedResult)
    }

    @UiThreadTest
    @Test(expected = TimeoutException::class)
    fun verify_imageIsNotCaptured_whenImageIsCancelled() {
        lifeCycle.currentState = Lifecycle.State.STARTED
        val expectedResult = Uri.parse("imagePath")
        every { imageUriCreator.createAndGetNewImageUri() } returns expectedResult

        registry.expectedResult = expectedResult
        registry.isResultFailed = true

        cameraCapture.captureImage()
        cameraCapture.capturedImageUri.getOrAwaitValu()

    }

    @UiThreadTest
    @Test(expected = TimeoutException::class)
    fun verify_imageIsNotCaptured_whenImageUriIsNull() {
        lifeCycle.currentState = Lifecycle.State.STARTED
        val expectedResult = null
        every { imageUriCreator.createAndGetNewImageUri() } returns expectedResult

        registry.expectedResult = expectedResult
        registry.isResultFailed = false

        cameraCapture.captureImage()
        cameraCapture.capturedImageUri.getOrAwaitValu()

    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}