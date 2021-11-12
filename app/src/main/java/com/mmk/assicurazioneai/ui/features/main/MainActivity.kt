package com.mmk.assicurazioneai.ui.features.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mmk.assicurazioneai.databinding.ActivityMainBinding
import com.mmk.assicurazioneai.utils.extensions.setTranslucentStatusBar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.rootView)

        setTranslucentStatusBar()
    }


}