package com.mmk.assicurazioneai.ui.features.cardamage.camera


import androidx.navigation.fragment.findNavController


import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.RectF
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.mmk.assicurazioneai.R
import com.mmk.assicurazioneai.databinding.FragmentCameraBinding
import com.mmk.assicurazioneai.ui.base.BaseFragment
import com.mmk.assicurazioneai.ui.features.cardamage.damageresult.DamageResultDialogFragment
import com.mmk.assicurazioneai.utils.ImageUriCreator
import com.mmk.assicurazioneai.utils.binding.viewBinding
import com.mmk.assicurazioneai.utils.extensions.hasPermission
import com.mmk.assicurazioneai.utils.extensions.toast
import com.mmk.assicurazioneai.utils.observeSingleEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class CameraFragment : BaseFragment(R.layout.fragment_camera) {

    override val viewModel: CameraViewModel by viewModel()
    override val binding: FragmentCameraBinding by viewBinding(FragmentCameraBinding::inflate)

    val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                //Selecting image from gallery
                val imageUri = it.data?.data
                onGettingImageUri(imageUri)
            }
        }

    private lateinit var cameraCapture: CameraCapture

    private val cameraRequestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->

        if (isGranted) onCameraPermissionGranted()
        else context.toast(R.string.error_no_camera_permission)
    }

    private val readFileRequestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) onReadFilePermissionGranted()
    }


    override fun initView() {
        super.initView()
        cameraCapture = CameraCapture(ImageUriCreator(requireContext()))
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        lifecycleScope.launch {
            delay(400)
            askCameraPermission()
        }
    }

    override fun observeValues() {
        super.observeValues()
        cameraCapture.capturedImageUri.observe(viewLifecycleOwner) {
            onGettingImageUri(it)
        }
        viewModel.isFlashOn.observe(viewLifecycleOwner) {
            cameraCapture.setFlashEnabled(it)
        }
    }


    override fun setClicks() {
        super.setClicks()
        binding.capturePhotoBtn.setOnClickListener {
            cameraCapture.captureImage(requireContext(), binding.root)
        }
        binding.galleryButton.setOnClickListener {
            askReadFilesPermission()
        }
    }

    private fun askCameraPermission() {
        val cameraPermission = Manifest.permission.CAMERA
        if (hasPermission(cameraPermission)) onCameraPermissionGranted()
        else cameraRequestPermissionLauncher.launch(cameraPermission)
    }

    private fun askReadFilesPermission() {
        val readFilePermission = Manifest.permission.READ_EXTERNAL_STORAGE
        if (hasPermission(readFilePermission)) onReadFilePermissionGranted()
        else readFileRequestPermissionLauncher.launch(readFilePermission)
    }

    private fun onCameraPermissionGranted() {
        cameraCapture.start(
            requireContext(),
            viewLifecycleOwner,
            binding.cameraView.surfaceProvider
        )

    }

    private fun onReadFilePermissionGranted() {
        val intentGallery =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        resultLauncher.launch(intentGallery)
    }

    private fun onGettingImageUri(imageUri: Uri?) {
        imageUri?.let {
            findNavController().navigate(
                R.id.action_cameraFragment_to_carDamageAnalyzeFragment,
                bundleOf("imageUri" to it.toString())
            )
        }
    }

}