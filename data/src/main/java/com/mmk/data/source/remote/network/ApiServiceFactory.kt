package com.mmk.data.source.remote.network

import com.mmk.data.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiServiceFactory {

    fun createCarApiService(): CarApiService =
        retrofit.create(CarApiService::class.java)


    private val retrofit: Retrofit by lazy {
        Retrofit.Builder().client(provideOkHttpClient(provideHttpLogging()))
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(provideConverterFactory())
            .build()
    }

    private fun provideHttpLogging(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) builder.addInterceptor(interceptor)
        return builder.build()
    }

    private fun provideConverterFactory(): Converter.Factory {
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        return MoshiConverterFactory.create(moshi)
    }


}