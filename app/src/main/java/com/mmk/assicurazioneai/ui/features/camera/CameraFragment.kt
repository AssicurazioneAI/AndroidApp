package com.mmk.assicurazioneai.ui.features.camera


import androidx.navigation.fragment.findNavController


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.RectF
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.mmk.assicurazioneai.R
import com.mmk.assicurazioneai.databinding.FragmentCameraBinding
import com.mmk.assicurazioneai.ui.base.BaseFragment
import com.mmk.assicurazioneai.utils.ImageUriCreator
import com.mmk.assicurazioneai.utils.binding.viewBinding
import com.mmk.assicurazioneai.utils.extensions.toast
import com.mmk.assicurazioneai.utils.observeSingleEvent
import org.koin.androidx.viewmodel.ext.android.viewModel


class CameraFragment : BaseFragment(R.layout.fragment_camera) {

    private val viewModel: CameraViewModel by viewModel()

    private lateinit var cameraCapture: CameraCapture

    //    private lateinit var cameraCapture: CameraCapture
    override val binding: FragmentCameraBinding by viewBinding(FragmentCameraBinding::inflate)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (viewModel.imagePath.value.isNullOrEmpty()) {
                        isEnabled = false
                        requireActivity().onBackPressed()
                    } else {
                        viewModel.resetImagePath()
                        askCameraPermission()
                    }
                }
            })

    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->

        if (isGranted) onCameraPermissionGranted()
        else context.toast(R.string.error_no_camera_permission)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraCapture = CameraCapture(ImageUriCreator(requireContext()))
    }

    override fun initView() {
        super.initView()
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        askCameraPermission()
    }

    override fun observeValues() {
        super.observeValues()
        cameraCapture.capturedImageUri.observe(viewLifecycleOwner) {
            it?.let {
                viewModel.setImagePath(it)
                binding.rectangleView.drawRectBounds(listOf(RectF(100f,200f,500f,600f)))
            }
        }

        viewModel.imagePath.observe(viewLifecycleOwner) {
            it?.let {
                binding.imageView.setImageURI(Uri.parse(it))
            }
        }

        viewModel.onImageSent.observeSingleEvent(viewLifecycleOwner) {
            context.toast("Image is sent successfully")
        }

        viewModel.isFlashOn.observe(viewLifecycleOwner){
            cameraCapture.setFlashEnabled(it)
        }
    }

    override fun setClicks() {
        super.setClicks()
        binding.sendImageButton.setOnClickListener {
            viewModel.sendImage()
        }
        binding.capturePhotoBtn.setOnClickListener {
            cameraCapture.captureImage(requireContext(), binding.root)
        }

    }

    private fun askCameraPermission() {
        val cameraPermission = Manifest.permission.CAMERA
        val isPermissionGranted = ContextCompat.checkSelfPermission(
            requireContext(),
            cameraPermission
        ) == PackageManager.PERMISSION_GRANTED

        if (isPermissionGranted) onCameraPermissionGranted()
        else requestPermissionLauncher.launch(cameraPermission)
    }

    private fun onCameraPermissionGranted() {
        cameraCapture.start(
            requireContext(),
            viewLifecycleOwner,
            binding.cameraView.surfaceProvider
        )

    }


}