package com.mmk.data.di

import com.mmk.data.source.remote.car.FakeCarRemoteDataSource
import com.mmk.data.source.remote.car.CarRemoteDataSource
import com.mmk.data.source.remote.network.ApiServiceFactory
import org.koin.dsl.module

val remoteSourceModule = module {
    val apiService = ApiServiceFactory.createCarApiService()
    factory<CarRemoteDataSource> { FakeCarRemoteDataSource() }
}