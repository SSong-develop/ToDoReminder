package com.hks.kr.wifireminder.utils

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(snackBarText: String, timeLength: Int) {
    Snackbar.make(this, snackBarText, timeLength).run {
        addCallback(object : Snackbar.Callback() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                /**
                 * 테스트 코드 작성 가능
                 * 호출이 몇번 되었는지를 Count해준다던지?
                 */
                super.onDismissed(transientBottomBar, event)
            }

            override fun onShown(sb: Snackbar?) {
                /**
                 * 테스트 코드 작성 가능
                 * 호출이 몇번 되었는지를 Count해준다던지?
                 */
                super.onShown(sb)
            }
        })
        show()
    }
}

/**
 * Triggers a snackBar message when the value contained by snackbarTaskMessageLiveEvent is modified
 */
fun View.setUpSnackBar(
    lifecycleOwner: LifecycleOwner,
    snackBarEvent: LiveData<Event<Int>>,
    timeLength: Int
) {

    snackBarEvent.observe(lifecycleOwner) { event ->
        event.getContentIfNotHandled()?.let {
            showSnackBar(context.getString(it), timeLength)
        }
    }
}