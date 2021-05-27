package com.hks.kr.wifireminder.view

import android.content.Context
import android.util.AttributeSet
import android.widget.CheckedTextView

class ShadowCheckedTextView constructor(
    context : Context,
    attrs : AttributeSet? = null
) : androidx.appcompat.widget.AppCompatCheckedTextView(context,attrs){
    // 그림자가 있는 spinner를 원합니다.
    // 스피너의 선택된 아이템은 CheckedTextView라는 친구로 xml을 사용하는데
    // 이를 커스텀해서 그림자가 보여지도록 하면 될 거 같습니다.
}