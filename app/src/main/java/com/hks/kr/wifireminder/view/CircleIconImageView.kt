package com.hks.kr.wifireminder.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.hks.kr.wifireminder.utils.dpToPixel
import java.lang.Integer.min

/**
 * 제대로 만든게 아니여서 프로젝트에서 사용하기엔 너무 많은 오류가 있을거 같음
 * 그냥 테스트 삼아 만들어봤다고 치면 될듯
 */
class CircleIconImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : AppCompatImageView(context, attrs) {

    private val bitmapPaint = Paint().apply {
        isAntiAlias = true
        color = Color.LTGRAY
    }

    init {
        setPadding(4.dpToPixel, 4.dpToPixel, 4.dpToPixel, 4.dpToPixel)
    }

    override fun onDraw(canvas: Canvas) {
        drawCircle(canvas)
        super.onDraw(canvas)
    }

    private fun drawCircle(canvas: Canvas) {
        canvas.drawCircle(width / 2f, height / 2f, min(width, height) / 2f, bitmapPaint)
    }
}