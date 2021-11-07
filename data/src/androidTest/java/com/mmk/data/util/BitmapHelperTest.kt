package com.mmk.data.util

import android.graphics.Bitmap
import android.util.Base64
import androidx.core.graphics.BitmapCompat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.mmk.domain.model.Result
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import io.mockk.mockkStatic

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.IllegalArgumentException


@RunWith(AndroidJUnit4::class)
class BitmapHelperTest {

    private lateinit var bitmapHelper: BitmapHelper
    private lateinit var testBitmap: Bitmap

    @Before
    fun setUp() {
        bitmapHelper = BitmapHelper()
        testBitmap = Bitmap.createBitmap(3000, 3000, Bitmap.Config.ARGB_8888)
    }

    @Test
    fun verify_bitmapSizeDecreased_whenResizeCalled() {
        val resizedBitmap = bitmapHelper.resize(testBitmap)
        val originalBitmapSize = BitmapCompat.getAllocationByteCount(testBitmap)
        val resizedBitmapSize = BitmapCompat.getAllocationByteCount(resizedBitmap!!)
        assertThat(resizedBitmapSize).isLessThan(originalBitmapSize)
    }

    @Test
    fun verifyOutputIsNull_GivenBitmapIsNull_whenResizeCalled() {
        val resizedBitmap = bitmapHelper.resize(null)
        assertThat(resizedBitmap).isNull()
    }

    @Test
    fun verify_outputIsNull_whenConvertNullBitmapToBase64() {
        val base64String: String? = bitmapHelper.convertBitmapToBase64String(null)
        assertThat(base64String).isNull()
    }

    @Test
    fun verify_outputIsBase64String_whenConvertBitmapToBase64() {
        mockkStatic(Base64::class)
        every { Base64.encodeToString(any(), any()) } returns  "encodedText"
        val base64String: String? = bitmapHelper.convertBitmapToBase64String(testBitmap)
        assertThat(base64String).isNotEmpty()
        assertThat(base64String).contains("base64")
    }

    @Test
    fun verify_outputIsNull_whenConvertBitmapToBase64ThrowsException() {
        mockkStatic(Base64::class)
        every { Base64.encodeToString(any(), any()) } throws Exception()
        val base64String: String? = bitmapHelper.convertBitmapToBase64String(testBitmap)
        assertThat(base64String).isNull()
    }

    @Test
    fun convertBitmapToBase64_shouldReturnNull_whenEncodingIsEmpty() {
        mockkStatic(Base64::class)
        every { Base64.encodeToString(any(), any()) } returns  ""
        val base64String: String? = bitmapHelper.convertBitmapToBase64String(testBitmap)
        assertThat(base64String).isNull()
    }


}