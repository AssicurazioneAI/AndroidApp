package com.mmk.assicurazioneai.ui.features.camera

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
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
    override val binding: FragmentCameraBinding by viewBinding(FragmentCameraBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraCapture =
            CameraCapture(
                requireActivity().activityResultRegistry,
                ImageUriCreator(requireContext())
            )

        lifecycle.addObserver(cameraCapture)

    }

    override fun initView() {
        super.initView()
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        cameraCapture.captureImage()
    }

    override fun observeValues() {
        super.observeValues()
        cameraCapture.capturedImageUri.observe(viewLifecycleOwner) {
            it?.let {
                viewModel.setImagePath(it)
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
    }

    override fun setClicks() {
        super.setClicks()
        binding.sendImageButton.setOnClickListener {
            viewModel.sendImage()
        }
    }


}