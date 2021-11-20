package com.mmk.data.di

import com.mmk.data.repository.CarRepositoryImpl
import com.mmk.domain.repository.cardamage.CarRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<CarRepository> { CarRepositoryImpl(get(), get()) }
}