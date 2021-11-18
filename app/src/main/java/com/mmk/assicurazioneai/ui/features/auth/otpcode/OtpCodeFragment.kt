package com.mmk.assicurazioneai.ui.features.auth.otpcode

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import az.reseptron.patient.util.KeyboardEventListener
import com.mmk.assicurazioneai.R
import com.mmk.assicurazioneai.databinding.FragmentCameraBinding
import com.mmk.assicurazioneai.databinding.FragmentLoginBinding
import com.mmk.assicurazioneai.databinding.FragmentOtpcodeBinding
import com.mmk.assicurazioneai.ui.base.BaseFragment
import com.mmk.assicurazioneai.ui.features.camera.CameraViewModel
import com.mmk.assicurazioneai.utils.binding.viewBinding
import com.mmk.assicurazioneai.utils.extensions.showKeyboard
import com.mmk.assicurazioneai.utils.observeSingleEvent
import org.koin.androidx.viewmodel.ext.android.viewModel

class OtpCodeFragment : BaseFragment(R.layout.fragment_otpcode) {


    override val binding: FragmentOtpcodeBinding by viewBinding(FragmentOtpcodeBinding::inflate)
    private val viewModel: OtpCodeViewModel by viewModel()

    private lateinit var phoneNumber: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)

    }

    override fun initView() {
        super.initView()
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.codePinView.requestFocus()
        showKeyboard(binding.codePinView)
        phoneNumber = arguments?.getString("phoneNumber") ?: ""
        binding.subTitle.text = getString(R.string.msg_verification_code_sent_phone, phoneNumber)
        binding.codePinView.doAfterTextChanged {
            if (it?.length == 4) viewModel.onClickVerifyButton()
        }
    }

    override fun observeValues() {
        super.observeValues()
        viewModel.onCorrectPinCode.observeSingleEvent(viewLifecycleOwner){
            findNavController().navigate(R.id.action_otpCodeFragment_to_cameraFragment)
        }
        viewModel.onCountDownTimer.observe(viewLifecycleOwner) {
            binding.resendButton.text = getString(R.string.txt_btn_resend_code_in_sec, it)
        }
        viewModel.onPinCodeTimeOut.observe(viewLifecycleOwner){
            binding.resendButton.isEnabled=it
            if (it) binding.resendButton.text=getString(R.string.txt_btn_resend_code)
            binding.resendButton.alpha=if (it) 1f else 0.5f
        }

    }

    override fun setClicks() {
        super.setClicks()
        binding.resendButton.setOnClickListener {
            viewModel.resendVerificationCode()
        }
    }


}