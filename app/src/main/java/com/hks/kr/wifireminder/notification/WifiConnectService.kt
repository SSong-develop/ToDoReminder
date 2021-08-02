package com.hks.kr.wifireminder.notification

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.hks.kr.wifireminder.utils.NetworkConnectChecker

/**
 * service에서 알림은 주는건 wifi가 끊어진다거나( 바깥을 나갔다가 이제 집에 들어온 )상황에 알림이 오도록 하고 즉 , remind해주는 channel 하나
 * 이제 알림을 보고 클릭했을때 다시 알림을 하나 보내는데 그때는 해야할 리스트들이 적혀있는 알림 , 미리 볼 수 있도록 하는 channel 하나
 *
 * TODO : 서비스 부분 작업과 BroadCast Receiver 작업은 좀 더 공부하면서 작업
 */

class WifiConnectService : Service() {

    override fun onBind(intent: Intent): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val networkConnectChecker = NetworkConnectChecker(this)
        networkConnectChecker.registerNetworkCallback()
        return START_STICKY
    }
}