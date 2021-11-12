package com.mmk.assicurazioneai.ui.features.auth.otpcode

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import az.reseptron.patient.util.KeyboardEventListener
import com.mmk.assicurazioneai.R
import com.mmk.assicurazioneai.databinding.FragmentLoginBinding
import com.mmk.assicurazioneai.databinding.FragmentOtpcodeBinding
import com.mmk.assicurazioneai.utils.extensions.showKeyboard

class OtpCodeFragment : Fragment(R.layout.fragment_otpcode) {


    private lateinit var binding: FragmentOtpcodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOtpcodeBinding.bind(view)
        binding.codePinView.requestFocus()
        showKeyboard(binding.codePinView)

        binding.resendButton.setOnClickListener {
            findNavController().navigate(R.id.action_otpCodeFragment_to_cameraFragment)
        }

    }


}