package com.mmk.assicurazioneai.ui.features.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mmk.assicurazioneai.R

class SplashFragment : Fragment(R.layout.fragment_splash) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.actionBar?.hide()
    }
}