package com.mmk.assicurazioneai.ui.base

import android.app.Application
import com.mmk.assicurazioneai.BuildConfig
import timber.log.Timber

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }
}