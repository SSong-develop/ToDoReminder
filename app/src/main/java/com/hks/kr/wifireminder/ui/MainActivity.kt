package com.hks.kr.wifireminder.ui

import android.app.NotificationManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.hks.kr.wifireminder.R
import com.hks.kr.wifireminder.databinding.ActivityMainBinding
import com.hks.kr.wifireminder.notification.WifiConnectService
import com.hks.kr.wifireminder.utils.createTaskNotificationChannel
import com.hks.kr.wifireminder.utils.createWifiNotificationChannel
import com.hks.kr.wifireminder.utils.startService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 *
 * TODO : Task를 끝낸 것에 대한 인증을 할 수 있으면 좋겠다.
 * 예를 들어 사진으로 찍어서 이를 보관하고 나중에 내가 이러한 일들을 해냈다는 것을 유저들이 확인할 수 있도록 하는 그런것 처럼!
 * 카메라Api, View 등등만 하면 될 거 같음. -> 재밌을 거 같다.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_main) }
    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var notificationManager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            lifecycleOwner = this@MainActivity
            activity = this@MainActivity
            vm = viewModel
        }

        configureStatusBarColor()
        observeIsAlreadyDoneCode()
    }

    private fun configureStatusBarColor() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
    }

    private fun observeIsAlreadyDoneCode() {
        viewModel.isSingleInvoked.observe(this) {
            if (!it) {
                viewModel.runSingleInvoke {
                    viewModel.initializeNotificationWorker()
                    viewModel.initializeDeleteWorker()
                    viewModel.initializeDefaultCategories()
                    notificationManager.createWifiNotificationChannel(this)
                    notificationManager.createTaskNotificationChannel(this)
                    startService<WifiConnectService>()
                }
            }
        }
    }

    fun navigateToAddTask() {
        // TODO : Activity로 작업하는게 더 나아보임
        findNavController(R.id.activity_nav_host_fragment).navigate(R.id.action_frameFragment_to_addTaskFragment)
    }
}
