package com.mmk.data.di

import com.mmk.data.source.remote.image.FakeImageRemoteDataSource
import com.mmk.data.source.remote.image.ImageRemoteDataSource
import org.koin.dsl.module

val remoteSourceModule = module {
    factory<ImageRemoteDataSource> { FakeImageRemoteDataSource() }
}