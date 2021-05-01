package com.hks.kr.wifireminder.application

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import com.hks.kr.wifireminder.utils.Keystore
import com.hks.kr.wifireminder.utils.PixelRatio
import com.hks.kr.wifireminder.utils.VersionCheckUtils
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        initializedSingleton()
        WorkManager.initialize(
            this,
            Configuration.Builder().setWorkerFactory(workerFactory).build()
        )
    }

    private fun initializedSingleton() {
        sampleKeystore = Keystore
        versionCheckUtils = VersionCheckUtils
        pixelRatio = PixelRatio(this)
    }

    companion object {
        lateinit var sampleKeystore: Keystore
        lateinit var versionCheckUtils: VersionCheckUtils
        lateinit var pixelRatio : PixelRatio
    }

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

}