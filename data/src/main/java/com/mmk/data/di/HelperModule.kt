package com.mmk.data.di

import com.mmk.data.util.BitmapHelper
import com.mmk.data.util.ImageHelper
import org.koin.dsl.module

val helperModule = module {
    factory<BitmapHelper> { BitmapHelper() }
    factory<ImageHelper> { ImageHelper(get(), get()) }
}