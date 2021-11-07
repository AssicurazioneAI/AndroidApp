package com.mmk.assicurazioneai.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import com.mmk.assicurazioneai.BuildConfig
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ImageUriCreator(private val context: Context?) {

    fun createAndGetNewImageUri(): Uri? {
        val imageFile = createNewImageFile()
        return getImageUriFromFile(imageFile)
    }

    private fun createNewImageFile(): File? = context?.let {
        // Create an image file name
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val rootDirectory: File? = it.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        try {
            File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                rootDirectory /* directory */
            )
        } catch (e: IOException) {
            null
        }
    }


    private fun getImageUriFromFile(imageFile: File?): Uri? = imageFile?.let {
        FileProvider.getUriForFile(
            context!!,
            "${BuildConfig.APPLICATION_ID}.android.fileprovider",
            it
        )
    }
}


