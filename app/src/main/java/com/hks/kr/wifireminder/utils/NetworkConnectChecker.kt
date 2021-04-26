package com.hks.kr.wifireminder.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest

class NetworkConnectChecker(
    private val context: Context
) : ConnectivityManager.NetworkCallback() {
    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        makeNotification("송훈기 일해라!!", context)
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        context.shortToast("wifi turns off")
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