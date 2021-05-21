package com.hks.kr.wifireminder.ui

import android.app.NotificationManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.hks.kr.wifireminder.R
import com.hks.kr.wifireminder.binding.AppBindingComponent
import com.hks.kr.wifireminder.binding.AppViewBinding
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

    private lateinit var mainFragmentFactory: MainFragmentFactory

    @Inject
    lateinit var notificationManager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        mainFragmentFactory = MainFragmentFactory.getInstance(this)
        supportFragmentManager.fragmentFactory = mainFragmentFactory
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(
                this,
                R.layout.activity_main,
                AppBindingComponent(lifecycleScope)
            )

        supportFragmentManager.commit {
            replace(
                R.id.activity_nav_host_fragment, supportFragmentManager.fragmentFactory.instantiate(
                    classLoader,
                    FrameFragment::class.java.name
                )
            )
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
                    notificationManager.createWifiNotificationChannel(this)
                    notificationManager.createTaskNotificationChannel(this)
                    startService<WifiConnectService>()
                }
            }
        }
    }
}
