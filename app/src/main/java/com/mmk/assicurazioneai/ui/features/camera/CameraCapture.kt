package com.mmk.assicurazioneai.ui.features.camera

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaActionSound
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import androidx.lifecycle.*
import com.mmk.assicurazioneai.utils.ImageUriCreator
import com.mmk.assicurazioneai.utils.extensions.toast
import timber.log.Timber
import java.io.File

class CameraCapture(
    private val imageUriCreator: ImageUriCreator
) {


    private var mPreview: Preview? = null
    private var mImageCapture: ImageCapture? = null
    private val mediaActionSound = MediaActionSound()
    private val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var isFlashEnabled = false


    private var _capturedImageUri = MutableLiveData<Uri?>()
    val capturedImageUri: LiveData<Uri?> = _capturedImageUri


    fun captureImage(context: Context?, containerView: View? = null) {
        if (mImageCapture == null || context == null) return
        mImageCapture!!.flashMode =
            if (isFlashEnabled) ImageCapture.FLASH_MODE_ON else ImageCapture.FLASH_MODE_OFF

        val newImageFile = imageUriCreator.createNewImageFile()
        newImageFile?.let { photoFile ->
            val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
            mediaActionSound.play(MediaActionSound.SHUTTER_CLICK)
            flashImage(containerView)
            mImageCapture!!.takePicture(outputOptions, ContextCompat.getMainExecutor(context),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        ProcessCameraProvider.getInstance(context).get().unbindAll()
                        _capturedImageUri.value = imageUriCreator.getImageUriFromFile(photoFile)


                    }

                    override fun onError(exception: ImageCaptureException) {
                        Timber.e("ImageCapture Exception: $exception")
                    }

                })


        }


    }


    fun start(
        context: Context?,
        lifecycleOwner: LifecycleOwner,
        surfaceProvider: Preview.SurfaceProvider
    ) {
        context?.let {
            val cameraProviderFuture = ProcessCameraProvider.getInstance(it)
            cameraProviderFuture.addListener({
                try {
                    // Used to bind the lifecycle of cameras to the lifecycle owner
                    val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
                    initialize(surfaceProvider)
                    // Unbind use cases before rebinding
                    cameraProvider.unbindAll()

                    // Bind use cases to camera
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        mPreview,
                        mImageCapture
                    )


                } catch (exc: Exception) {
                    Timber.e("Use case binding failed $exc")
                }

            }, ContextCompat.getMainExecutor(it))

        }


    }

    fun setFlashEnabled(isEnabled: Boolean) {
        isFlashEnabled = isEnabled
    }

    private fun initialize(surfaceProvider: Preview.SurfaceProvider) {
        /**
         * Initialize Camera Preview
         **/
        mPreview = Preview.Builder()
            .build()
            .also { it.setSurfaceProvider(surfaceProvider) }

        /**
         * Initialize Camera Capture
         **/
        mImageCapture = ImageCapture.Builder()
            .setFlashMode(if (isFlashEnabled) ImageCapture.FLASH_MODE_ON else ImageCapture.FLASH_MODE_OFF)
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .build()

    }


    // Display flash animation to indicate that photo was captured
    private fun flashImage(container: View?) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return
        container?.postDelayed({
            container.foreground = ColorDrawable(Color.WHITE)
            container.postDelayed(
                { container.foreground = null }, 50L
            )
        }, 50L)
    }


}