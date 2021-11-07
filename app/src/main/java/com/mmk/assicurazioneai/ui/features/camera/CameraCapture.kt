package com.mmk.assicurazioneai.ui.features.camera

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.*
import com.mmk.assicurazioneai.utils.ImageUriCreator

class CameraCapture(
    private val registry: ActivityResultRegistry,
    private val imageUriCreator: ImageUriCreator
) : DefaultLifecycleObserver {


    private lateinit var cameraIntentResultLauncher: ActivityResultLauncher<Intent>

    private var _capturedImageUri = MutableLiveData<Uri?>()
    val capturedImageUri: LiveData<Uri?> = _capturedImageUri

    private var imageUri: Uri? = null


    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        cameraIntentResultLauncher = registry.register(
            "key",
            owner,
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK)
                _capturedImageUri.value = imageUri
        }
    }

    fun captureImage() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val newImageUri = imageUriCreator.createAndGetNewImageUri()
        newImageUri?.let {
            imageUri = it
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, it)
            cameraIntentResultLauncher.launch(takePictureIntent)
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        cameraIntentResultLauncher.unregister()
        super.onDestroy(owner)
    }

}