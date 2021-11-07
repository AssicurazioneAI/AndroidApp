package com.mmk.assicurazioneai.ui.base

import android.app.Application
import com.mmk.assicurazioneai.BuildConfig
import com.mmk.assicurazioneai.di.viewModelModule
import com.mmk.data.di.helperModule
import com.mmk.data.di.remoteSourceModule
import com.mmk.data.di.repositoryModule
import com.mmk.domain.di.interactionModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(domainModules + dataModules + appModules)
        }
    }

    val domainModules = listOf(interactionModule)
    val dataModules = listOf(remoteSourceModule, repositoryModule, helperModule)
    val appModules = listOf(viewModelModule)
}