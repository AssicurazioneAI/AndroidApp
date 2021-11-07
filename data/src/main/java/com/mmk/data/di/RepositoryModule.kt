package com.mmk.data.di

import com.mmk.data.repository.ImageRepositoryImpl
import com.mmk.domain.repository.image.ImageRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<ImageRepository> { ImageRepositoryImpl(get(), get()) }
}