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
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {


    suspend fun getBase64StringFromImagePath(imagePath: String): String? {
        val bitmap = context.getBitmapFromImagePath(imagePath)
        return bitmap?.let {
            withContext(dispatcher) {
                val resizedBitmap = it.resize()
                resizedBitmap.asBase64()
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


    private fun Bitmap.resize(maxWidth: Int = 1000, maxHeight: Int = 1000): Bitmap {
        val width = this.width
        val height = this.height
        val scaleHeight = maxHeight.toFloat() / width
        val scaleWidth = maxWidth.toFloat() / height
        val scale = min(scaleHeight, scaleWidth)

        val matrix = Matrix()
        matrix.postScale(scale, scale)
        val resizedBitmap = Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
        return resizedBitmap
    }

    private fun Bitmap?.asBase64(): String? {
        return try {
            val baos = ByteArrayOutputStream()
            this?.compress(Bitmap.CompressFormat.JPEG, 70, baos)
            val byteArray = baos.toByteArray()
            val encodedImage = Base64.encodeToString(byteArray, Base64.NO_WRAP)
            baos.close()
            val prefix = "data:image/jpeg;base64,"
            "$prefix$encodedImage"

        } catch (e: Exception) {
            Timber.e(e)
            e.toString()
        }
    }


}