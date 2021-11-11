package com.mmk.assicurazioneai.ui.features.auth.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import az.reseptron.patient.util.KeyboardEventListener
import com.mmk.assicurazioneai.R
import com.mmk.assicurazioneai.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding:FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentLoginBinding.bind(view)

    }

    override fun onResume() {
        super.onResume()
        KeyboardEventListener(binding.scrollView){isOpen, keyboardHeight ->
            if (isOpen) binding.imageLogo.animate().alpha(1f)
            else binding.imageLogo.animate().alpha(0f)
        }
    }

}