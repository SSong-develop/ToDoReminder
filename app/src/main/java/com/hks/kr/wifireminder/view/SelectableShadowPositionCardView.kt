package com.hks.kr.wifireminder.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import com.hks.kr.wifireminder.R
import com.hks.kr.wifireminder.utils.dpToPixelFloat
import java.lang.Float.MIN_VALUE

class SelectableShadowPositionCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val defaultShadowColor = Color.BLACK

    private val defaultRectBackgroundColor = Color.WHITE

    private val shadowPaint = Paint()
    private val borderPaint = Paint()
    private val rectPaint = Paint()

    private val shadowTopPath = Path()
    private val shadowBottomPath = Path()
    private val shadowStartPath = Path()
    private val shadowEndPath = Path()

    private val rectBackgroundPath = Path()
    private val clipPath = Path()

    // RectF
    private val borderRectF = RectF()
    private val clipRectF = RectF()
    private val rectBackgroundRectF = RectF()

    private val porterDuffXferMode = PorterDuffXfermode(PorterDuff.Mode.SRC)

    private var shadowColor = defaultShadowColor

    private var shadowStrokeWidth = 15.toFloat()

    private var borderColor = Color.BLACK
    private var blurRadius = 40.toFloat()
    private var shadowStartY = MIN_VALUE
    private var shadowEndOffset = 0f
    private var shadowStartOffset = 0f
    private var shadowTopOffset = 0f
    private var shadowBottomOffset = 0f
    private var enableShadow = true
    private var enableBorder = false
    private var enableShadowTop = false
    private var enableShadowBottom = true
    private var enableShadowStart = true
    private var enableShadowEnd = true
    private var borderHeight = 0f
    private var cornerRadius = 16f

    private val blurMaskFilter = BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL)

    init {
        setBackgroundColor(defaultRectBackgroundColor)
        if (attrs != null) {
            getStyleableAttrs(attrs)
        }
    }

    private fun getStyleableAttrs(attrs: AttributeSet) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.ShadowConstraintLayout, 0, 0).use {
            shadowTopOffset = it.getDimension(
                R.styleable.ShadowConstraintLayout_shadowTopOffset,
                0.dpToPixelFloat
            )
            shadowBottomOffset = it.getDimension(
                R.styleable.ShadowConstraintLayout_shadowBottomOffset,
                0.dpToPixelFloat
            )
            shadowStartOffset = it.getDimension(
                R.styleable.ShadowConstraintLayout_shadowStartOffset,
                0.dpToPixelFloat
            )
            shadowEndOffset = it.getDimension(
                R.styleable.ShadowConstraintLayout_shadowEndOffset,
                0.dpToPixelFloat
            )
            shadowStartY = it.getDimension(
                R.styleable.ShadowConstraintLayout_shadowStartY,
                MIN_VALUE
            )
            shadowStrokeWidth = it.getDimension(
                R.styleable.ShadowConstraintLayout_shadowStrokeWidth,
                4.dpToPixelFloat
            )
            cornerRadius = it.getDimension(
                R.styleable.ShadowConstraintLayout_cornerRadius,
                4.dpToPixelFloat
            )
            blurRadius = it.getDimension(
                R.styleable.ShadowConstraintLayout_blurRadius,
                16.dpToPixelFloat
            )
            borderHeight = it.getDimension(
                R.styleable.ShadowConstraintLayout_borderHeight,
                0f
            )
            shadowColor = it.getColor(
                R.styleable.ShadowConstraintLayout_shadowColor,
                Color.GRAY
            )
            borderColor = it.getColor(
                R.styleable.ShadowConstraintLayout_borderColor,
                Color.BLACK
            )
            enableShadow =
                it.getBoolean(R.styleable.ShadowConstraintLayout_enableShadow, true)
            enableBorder =
                it.getBoolean(R.styleable.ShadowConstraintLayout_enableBorder, false)
            enableShadowTop =
                it.getBoolean(R.styleable.ShadowConstraintLayout_enableShadowTop, false)
            enableShadowBottom =
                it.getBoolean(R.styleable.ShadowConstraintLayout_enableShadowBottom, true)
            enableShadowStart =
                it.getBoolean(R.styleable.ShadowConstraintLayout_enableShadowStart, true)
            enableShadowEnd =
                it.getBoolean(R.styleable.ShadowConstraintLayout_enableShadowEnd, true)
        }
    }

    override fun dispatchDraw(canvas: Canvas) {
        clipRoundCorners(canvas)
        super.dispatchDraw(canvas)
    }

    override fun onDraw(canvas: Canvas) {
        if (enableShadow) {
            initializeShadowAttribute()
            if (enableShadowTop) drawShadowTop(canvas)
            if (enableShadowBottom) drawShadowBottom(canvas)
            if (enableShadowStart) drawShadowStart(canvas)
            if (enableShadowEnd) drawShadowEnd(canvas)

        }
        drawRectBackground(canvas)
        if (enableBorder) drawBorder(canvas)
        super.onDraw(canvas)
    }

    private fun initializeShadowAttribute() {
        shadowPaint.apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            color = shadowColor
            strokeWidth = shadowStrokeWidth
            xfermode = porterDuffXferMode
            maskFilter = blurMaskFilter
        }
        shadowTopOffset = 6.dpToPixelFloat
        shadowBottomOffset = (-2).dpToPixelFloat
        shadowStartOffset = 2.dpToPixelFloat
        shadowEndOffset = (-2).dpToPixelFloat
    }

    private fun clipRoundCorners(canvas: Canvas) {
        clipPath.reset()

        clipRectF.apply {
            top = 0f
            left = 0f
            right = canvas.width.toFloat()
            bottom = canvas.height.toFloat()
        }
        clipPath.addRoundRect(clipRectF, cornerRadius, cornerRadius, Path.Direction.CW)
    }

    private fun drawBorder(canvas: Canvas) {
        // set Paint
        borderPaint.apply {
            style = Paint.Style.STROKE
            color = borderColor
            strokeWidth = 0.5f
        }

        // provide Rect
        borderRectF.apply {
            top = height - borderHeight
            left = 0f
            right = width.toFloat()
            bottom = height.toFloat()
        }
        // corner가 둥근 rect그리기
        canvas.drawRoundRect(borderRectF, cornerRadius, cornerRadius, borderPaint)
    }

    private fun drawRectBackground(canvas: Canvas) {
        rectPaint.apply {
            style = Paint.Style.FILL
            color = Color.WHITE
            xfermode = porterDuffXferMode
        }

        rectBackgroundRectF.apply {
            top = 0f
            left = 0f
            right = width.toFloat()
            bottom = height.toFloat()
        }

        rectBackgroundPath.apply {
            reset()
            addRect(rectBackgroundRectF, Path.Direction.CW)
        }

        canvas.drawRoundRect(rectBackgroundRectF, cornerRadius, cornerRadius, rectPaint)
    }

    private fun drawShadowTop(canvas: Canvas) {
        shadowTopPath.apply {
            reset()
            moveTo((width + (shadowEndOffset)), shadowStartY + shadowTopOffset)
            lineTo((shadowStartOffset), shadowStartY + shadowTopOffset)
        }
        canvas.drawPath(shadowTopPath, shadowPaint)
        canvas.save()
    }

    private fun drawShadowStart(canvas: Canvas) {
        shadowStartPath.apply {
            reset()
            moveTo((shadowStartOffset), shadowStartY + shadowTopOffset)
            lineTo((shadowStartOffset), (height + shadowBottomOffset))
        }
        canvas.drawPath(shadowStartPath, shadowPaint)
        canvas.save()
    }

    private fun drawShadowBottom(canvas: Canvas) {
        shadowBottomPath.apply {
            reset()
            moveTo((shadowStartOffset), (height + shadowBottomOffset))
            lineTo((width + shadowEndOffset), (height + shadowBottomOffset))
        }
        canvas.drawPath(shadowBottomPath, shadowPaint)
        canvas.save()
    }

    private fun drawShadowEnd(canvas: Canvas) {
        shadowEndPath.apply {
            reset()
            moveTo((width + shadowEndOffset), (height + shadowBottomOffset))
            lineTo((width + shadowEndOffset), shadowStartY + shadowTopOffset)
        }
        canvas.drawPath(shadowEndPath, shadowPaint)
        canvas.save()
    }

}