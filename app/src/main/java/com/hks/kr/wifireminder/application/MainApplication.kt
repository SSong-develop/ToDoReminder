package com.hks.kr.wifireminder.application

import android.app.Application
import com.hks.kr.wifireminder.utils.Keystore
import com.hks.kr.wifireminder.utils.VersionCheckUtils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initializedSingleton()
    }

    private fun initializedSingleton() {
        sampleKeystore = Keystore
        versionCheckUtils = VersionCheckUtils
    }

    companion object {
        lateinit var sampleKeystore: Keystore
        lateinit var versionCheckUtils: VersionCheckUtils
    }
}