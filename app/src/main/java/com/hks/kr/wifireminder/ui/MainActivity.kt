package com.hks.kr.wifireminder.ui

import android.app.NotificationManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.hks.kr.wifireminder.R
import com.hks.kr.wifireminder.databinding.ActivityMainBinding
import com.hks.kr.wifireminder.notification.WifiConnectService
import com.hks.kr.wifireminder.ui.frame.FrameFragment
import com.hks.kr.wifireminder.utils.createTaskNotificationChannel
import com.hks.kr.wifireminder.utils.createWifiNotificationChannel
import com.hks.kr.wifireminder.utils.startService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var notificationManager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_app_bar_fragment, FrameFragment()).commit()
        observeIsAlreadyDoneCode()
    }

    private fun observeIsAlreadyDoneCode() {
        viewModel.isSingleInvoked.observe(this) {
            if (!it) {
                viewModel.runSingleInvoke {
                    notificationManager.createWifiNotificationChannel(this)
                    notificationManager.createTaskNotificationChannel(this)
                    startService<WifiConnectService>()
                }
            }
        }
    }
}
