package com.mmk.data.util

import android.util.Base64

class ImageHelper {

    fun getBase64StringFromImagePath(imagePath:String):String?{
        return Base64.encodeToString(imagePath.toByteArray(), Base64.NO_WRAP)
    }
}