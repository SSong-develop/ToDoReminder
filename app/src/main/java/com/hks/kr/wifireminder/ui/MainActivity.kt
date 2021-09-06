package com.hks.kr.wifireminder.ui

import android.app.NotificationManager
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.hks.kr.wifireminder.R
import com.hks.kr.wifireminder.binding.AppBindingComponent
import com.hks.kr.wifireminder.databinding.ActivityMainBinding
import com.hks.kr.wifireminder.notification.WifiConnectService
import com.hks.kr.wifireminder.utils.createTaskNotificationChannel
import com.hks.kr.wifireminder.utils.createWifiNotificationChannel
import com.hks.kr.wifireminder.utils.startService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var notificationManager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main,
            AppBindingComponent(lifecycleScope)
        )
        binding.lifecycleOwner = this
        binding.activity = this
        binding.bindViewModel = viewModel

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
        viewModel.setFabVisible(false)
        findNavController(R.id.activity_nav_host_fragment).navigate(R.id.action_frameFragment_to_addTaskFragment)
    }
}
