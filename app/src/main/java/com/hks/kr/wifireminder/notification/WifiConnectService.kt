package com.hks.kr.wifireminder.notification

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.hks.kr.wifireminder.utils.NetworkConnectChecker

/**
 * 할일에 대한 목록이 나오려면 적어도 viewModel에서 이걸 쏴줘야 하는데
 * why? viewModel에서 local에 저장된 데이터들을 전부 긁어서 message로 만들어서 줄테니까
 * 근데 그럼 문제는 앱을 켜놔야 한다는 거고 그런거죠
 *
 * service에서 알림은 주는건 wifi가 끊어진다거나( 바깥을 나갔다가 이제 집에 들어온 )상황에 알림이 오도록 하고 즉 , remind해주는 channel 하나
 * 이제 알림을 보고 클릭했을때 다시 알림을 하나 보내는데 그때는 해야할 리스트들이 적혀있는 알림 , 미리 볼 수 있도록 하는 channel 하나
 */

class WifiConnectService : Service() {

    override fun onCreate() {
        super.onCreate()
        val networkConnectChecker = NetworkConnectChecker(this)
        networkConnectChecker.registerNetworkCallback()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}