package com.hks.kr.wifireminder.utils

import android.annotation.SuppressLint
import android.os.Build

object VersionCheckUtils {
    fun isVersionUpToOreo(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

    @SuppressLint("ObsoleteSdkInt")
    fun isVersionUpToM(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
}