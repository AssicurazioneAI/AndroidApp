package com.mmk.data.util

import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Base64
import timber.log.Timber
import java.io.ByteArrayOutputStream
import kotlin.math.min

class BitmapHelper {

    fun resize(bitmap: Bitmap?, maxWidth: Int = 1000, maxHeight: Int = 1000): Bitmap? {
        return bitmap?.let {
            val width = it.width
            val height = it.height
            val scaleHeight = maxHeight.toFloat() / width
            val scaleWidth = maxWidth.toFloat() / height
            val scale = min(scaleHeight, scaleWidth)
            val matrix = Matrix()
            matrix.postScale(scale, scale)
            try {
                val resizedBitmap = Bitmap.createBitmap(it, 0, 0, width, height, matrix, true)
                resizedBitmap
            } catch (e: Exception) {
                Timber.e(e)
                null
            }

        }
    }

    fun convertBitmapToBase64String(bitmap: Bitmap?): String? {
        return try {
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            val encodedImage = Base64.encodeToString(byteArray, Base64.NO_WRAP)
            byteArrayOutputStream.close()
            val prefix = "data:image/jpeg;base64,"
            if (encodedImage.isNullOrEmpty().not())
                return "$prefix$encodedImage"
            else null
        } catch (e: Exception) {
            Timber.e(e)
            null
        }
    }

}


