package com.mmk.domain.di

import com.mmk.domain.usecase.cardamage.GettingCarDamageUseCase
import com.mmk.domain.usecase.cardamage.GettingCarDamageUseCaseImpl
import org.koin.dsl.module

val interactionModule = module {
    factory<GettingCarDamageUseCase> { GettingCarDamageUseCaseImpl(get()) }
}