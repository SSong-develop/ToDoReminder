package com.hks.kr.wifireminder.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast

fun Context.shortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

inline fun <reified T : Any> Context.getIntent() = Intent(this, T::class.java)

inline fun <reified T : Any> Context.startService() = startService(getIntent<T>())