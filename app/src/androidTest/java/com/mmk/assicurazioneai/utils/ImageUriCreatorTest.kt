package com.mmk.assicurazioneai.utils

import android.net.Uri
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.File
import java.io.IOException

class ImageUriCreatorTest {

    private lateinit var imageUriCreator: ImageUriCreator

    @Before
    fun setup() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        imageUriCreator = ImageUriCreator(appContext)
    }

    @Test
    fun verify_CreatedImage_isNull_whenNoContext() {
        val nullImageUtil = ImageUriCreator(null)
        val imageUri: Uri? = nullImageUtil.createAndGetNewImageUri()
        assertThat(imageUri).isNull()
    }

    @Test
    fun verify_imageIsCreated_whenContextIsProvided() {
        val imageUri: Uri? = imageUriCreator.createAndGetNewImageUri()
        assertThat(imageUri).isNotNull()
    }

    @Test
    fun verify_imageIsNull_whenExceptionIsThrown() {
        mockkStatic(File::class)
        every { File.createTempFile(any(), any(), any()) } throws IOException()
        val imageUri: Uri? = imageUriCreator.createAndGetNewImageUri()
        assertThat(imageUri).isNull()

    }

    @Test
    fun test_imageUriIsNull_whenFileCannotCreated() {
        mockkStatic(File::class)
        every { File.createTempFile(any(), any(), any()) } returns null
        val imageUri = imageUriCreator.createAndGetNewImageUri()
        assertThat(imageUri).isNull()
    }


    @After
    fun tearDown() {
        unmockkAll()
    }
}