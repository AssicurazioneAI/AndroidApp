package com.mmk.assicurazioneai.ui.features.camera

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.mmk.assicurazioneai.BuildConfig
import com.mmk.assicurazioneai.R
import com.mmk.assicurazioneai.utils.ImageUtil
import com.mmk.assicurazioneai.utils.extensions.toast
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log


class CameraFragment : Fragment(R.layout.fragment_camera) {

    private lateinit var cameraCapture: CameraCapture

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraCapture =
            CameraCapture(requireActivity().activityResultRegistry, ImageUtil(requireContext()))

        lifecycle.addObserver(cameraCapture)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraCapture.captureImage()

        cameraCapture.capturedImageUri.observe(viewLifecycleOwner){
            it?.let {
                context.toast(it.toString())
            }

        }

    }


}