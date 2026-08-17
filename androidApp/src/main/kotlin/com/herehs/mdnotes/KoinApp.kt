package com.herehs.mdnotes

import android.app.Application
import com.herehs.mdnotes.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger


class KoinApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@KoinApp)
            androidLogger()
        }
    }
}