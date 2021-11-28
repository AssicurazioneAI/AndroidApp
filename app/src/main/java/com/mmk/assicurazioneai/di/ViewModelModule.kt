package com.mmk.assicurazioneai.di

import com.mmk.assicurazioneai.ui.features.auth.otpcode.OtpCodeViewModel
import com.mmk.assicurazioneai.ui.features.cardamage.analyze.CarDamageAnalyzeViewModel
import com.mmk.assicurazioneai.ui.features.cardamage.camera.CameraViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CameraViewModel() }
    viewModel { CarDamageAnalyzeViewModel(get()) }
    viewModel { OtpCodeViewModel() }
}