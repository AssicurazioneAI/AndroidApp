package com.mmk.domain.di

import com.mmk.domain.usecase.image.SendingImageUseCase
import com.mmk.domain.usecase.image.SendingImageUseCaseImpl
import org.koin.dsl.module

val interactionModule = module {
    factory<SendingImageUseCase> { SendingImageUseCaseImpl(get()) }
}