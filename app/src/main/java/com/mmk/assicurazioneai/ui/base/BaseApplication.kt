package com.mmk.assicurazioneai.ui.base

import android.app.Application
import android.os.Environment
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
import java.io.File

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
        deleteImageFiles()
    }

    private fun deleteImageFiles() {
        val rootDirectory: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        rootDirectory?.deleteRecursively()

    }

    val domainModules = listOf(interactionModule)
    val dataModules = listOf(remoteSourceModule, repositoryModule, helperModule)
    val appModules = listOf(viewModelModule)
}