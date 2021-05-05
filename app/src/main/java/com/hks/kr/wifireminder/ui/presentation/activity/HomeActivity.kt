package com.hks.kr.wifireminder.ui.presentation.activity

import android.app.NotificationManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.hks.kr.wifireminder.R
import com.hks.kr.wifireminder.databinding.ActivityHomeBinding
import com.hks.kr.wifireminder.notification.WifiConnectService
import com.hks.kr.wifireminder.ui.viewmodel.HomeViewModel
import com.hks.kr.wifireminder.utils.createTaskNotificationChannel
import com.hks.kr.wifireminder.utils.createWifiNotificationChannel
import com.hks.kr.wifireminder.utils.startService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var notificationManager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityHomeBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_home)

        observeIsAlreadyDoneCode()
    }

    private fun observeIsAlreadyDoneCode(){
        viewModel.isAlreadyDoneCode.observe(this) {
            if(!it){
                viewModel.runSingleInvoke{
                    notificationManager.createWifiNotificationChannel(this)
                    notificationManager.createTaskNotificationChannel(this)
                    startService<WifiConnectService>()
                }
            }
        }
    }
}
