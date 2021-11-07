package com.mmk.assicurazioneai.di

import com.mmk.assicurazioneai.ui.features.camera.CameraViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CameraViewModel(get()) }
}