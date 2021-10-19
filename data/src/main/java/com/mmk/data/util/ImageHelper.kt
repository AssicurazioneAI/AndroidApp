package com.mmk.data.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.IOException
import kotlin.math.min

class ImageHelper(
    private val context: Context,
    private val bitmapHelper: BitmapHelper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {


    suspend fun getBase64StringFromImagePath(imagePath: String): String? {
        val bitmap = context.getBitmapFromImagePath(imagePath)
        return bitmap?.let {
            withContext(dispatcher) {
                val resizedBitmap = bitmapHelper.resize(it)
                bitmapHelper.convertBitmapToBase64String(resizedBitmap)
            }
        }

    }


    private fun Context.getBitmapFromImagePath(imagePath: String): Bitmap? {
        val imageUri = Uri.parse(imagePath)
        if (Build.VERSION.SDK_INT < 28) {
            return try {
                MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
            } catch (e: IOException) {
                Timber.e(e)
                null
            }

        } else {
            val source = ImageDecoder.createSource(contentResolver, imageUri)
            return try {
                ImageDecoder.decodeBitmap(source)
            } catch (e: IOException) {
                Timber.e(e)
                null
            }
        }

    }
}