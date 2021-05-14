package com.hks.kr.wifireminder.utils

import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.hks.kr.wifireminder.application.MainApplication.Companion.pixelRatio

fun DialogFragment.resizeDialogSize(widthRatio : Float , heightRatio : Float){
    val dialogParams : WindowManager.LayoutParams = this.dialog!!.window!!.attributes

    (pixelRatio.screenWidth * widthRatio).toInt().also {
        dialogParams.width = it
    }
    (pixelRatio.screenHeight * heightRatio).toInt().also {
        dialogParams.height = it
    }

    this.dialog!!.window!!.apply {
        attributes = dialogParams
    }
}