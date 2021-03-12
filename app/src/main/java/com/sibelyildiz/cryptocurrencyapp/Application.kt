package com.sibelyildiz.cryptocurrencyapp

import android.app.Application
import com.sibelyildiz.cryptocurrencyapp.di.appModule
import com.sibelyildiz.cryptocurrencyapp.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Application)
            modules(listOf(appModule, networkModule))
        }
    }
}