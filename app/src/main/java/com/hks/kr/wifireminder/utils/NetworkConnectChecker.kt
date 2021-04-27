package com.hks.kr.wifireminder.utils

import android.app.NotificationManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.core.content.ContextCompat
import com.hks.kr.wifireminder.R

class NetworkConnectChecker(
    private val context: Context
) : ConnectivityManager.NetworkCallback() {
    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        val notificationManager =
            ContextCompat.getSystemService(
                context,
                NotificationManager::class.java
            ) as NotificationManager
        notificationManager.sendWifiNotification(
            context.getString(R.string.wifi_notification_text),
            context
        )
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        context.shortToast(context.getString(R.string.wifi_checker_onLost_text))
    }

    fun registerNetworkCallback() {
        val connectivityManager = context.getSystemService(ConnectivityManager::class.java)
        val networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, this)
    }

    fun terminateNetworkCallback() {
        val connectivityManager = context.getSystemService(ConnectivityManager::class.java)
        connectivityManager.unregisterNetworkCallback(this)
    }
}