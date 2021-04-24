package com.hks.kr.wifireminder.application

import android.app.Application
import com.hks.kr.wifireminder.utils.Keystore
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initializedSingleton()
    }

    private fun initializedSingleton() {
        sampleKeystore = Keystore
    }

    companion object{
        lateinit var sampleKeystore : Keystore
    }
}