package com.mmk.data.util

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.annotation.DrawableRes

import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import com.mmk.data.R
import io.mockk.every
import io.mockk.mockk

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ImageHelperTest {
    private lateinit var imageHelper: ImageHelper
    private lateinit var bitmapHelper: BitmapHelper
    private lateinit var context: Context


    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        bitmapHelper = mockk()
        imageHelper = ImageHelper(context, bitmapHelper, mainCoroutineRule.dispatcher)
    }

    @Test
    fun getBase64String_returnsNull_whenImageIsNotFound() = mainCoroutineRule.runBlockingTest {
        val base64String = imageHelper.getBase64StringFromImagePath("NotCorrectImagePath")
        assertThat(base64String).isNull()
    }

    @Test
    fun getBase64String_returnsNull_whenGettingBitmapIsFailed() = mainCoroutineRule.runBlockingTest {
        val imagePath = getImagePathFromResId(R.mipmap.ic_launcher)
        every { bitmapHelper.resize(any()) } returns null
        every { bitmapHelper.convertBitmapToBase64String(any()) } returns null
        val base64String = imageHelper.getBase64StringFromImagePath(imagePath)
        assertThat(base64String).isNull()
    }

    @Test
    fun getBase64String_returnsBase64String_whenImageFound() = mainCoroutineRule.runBlockingTest {
        val imagePath = getImagePathFromResId(R.mipmap.ic_launcher)
        val base64String = imageHelper.getBase64StringFromImagePath(imagePath)
        assertThat(base64String).isNotEmpty()
    }

    private fun getImagePathFromResId(@DrawableRes resId: Int): String {
        return Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(context.resources.getResourcePackageName(resId))
            .appendPath(context.resources.getResourceTypeName(resId))
            .appendPath(context.resources.getResourceEntryName(resId))
            .build().toString()
    }
}
