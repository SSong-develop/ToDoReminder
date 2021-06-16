package com.hks.kr.wifireminder.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.hks.kr.wifireminder.R
import com.hks.kr.wifireminder.utils.OnChangeProp
import com.hks.kr.wifireminder.utils.dpToPixel
import com.hks.kr.wifireminder.utils.dpToPixelFloat
import java.lang.Float.MIN_VALUE

class SelectableShadowPositionCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val shadowPaint = Paint()
    private val borderPaint = Paint()
    private val layoutPaint = Paint()

    private val shadowTopPath = Path()
    private val shadowBottomPath = Path()
    private val shadowStartPath = Path()
    private val shadowEndPath = Path()

    private val layoutBackgroundPath = Path()

    private val borderRectF = RectF()
    private val layoutBackgroundRectF = RectF()

    private val porterDuffXferMode = PorterDuffXfermode(PorterDuff.Mode.SRC)

    private var shadowColor = Color.BLACK

    private var shadowStrokeWidth = 4.dpToPixelFloat

    private var borderColor = Color.BLACK
    private var blurRadius = 16.dpToPixelFloat
    private var shadowStartOffset = 0.dpToPixelFloat
    private var shadowEndOffset = 0.dpToPixelFloat
    private var shadowTopOffset = 0.dpToPixelFloat
    private var shadowBottomOffset = 0.dpToPixelFloat

    private var enableShadow = true
    private var enableBorder = false
    private var enableShadowTop = true
    private var enableShadowBottom = true
    private var enableShadowStart = true
    private var enableShadowEnd = true

    private val blurMaskFilter = BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL)

    var cornerRadius by OnChangeProp(16.dpToPixelFloat) {
        updateBackground()
    }

    var layoutBackgroundColor by OnChangeProp(Color.WHITE) {
        updateBackground()
    }

    init {
        if (attrs != null) {
            getStyleableAttrs(attrs)
        }
        updateBackground()
    }

    private fun getStyleableAttrs(attrs: AttributeSet) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.ShadowConstraintLayout, 0, 0).use {
            shadowTopOffset = it.getDimension(
                R.styleable.ShadowConstraintLayout_shadow_top_offset,
                shadowTopOffset
            )
            shadowBottomOffset = it.getDimension(
                R.styleable.ShadowConstraintLayout_shadow_bottom_offset,
                shadowBottomOffset
            )
            shadowStartOffset = it.getDimension(
                R.styleable.ShadowConstraintLayout_shadow_start_offset,
                shadowStartOffset
            )
            shadowEndOffset = it.getDimension(
                R.styleable.ShadowConstraintLayout_shadow_end_offset,
                shadowEndOffset
            )
            shadowStrokeWidth = it.getDimension(
                R.styleable.ShadowConstraintLayout_shadow_stroke_width,
                shadowStrokeWidth
            )
            cornerRadius = it.getDimension(
                R.styleable.ShadowConstraintLayout_corner_radius,
                cornerRadius
            )
            blurRadius = it.getDimension(
                R.styleable.ShadowConstraintLayout_blur_radius,
                blurRadius
            )
            shadowColor = it.getColor(
                R.styleable.ShadowConstraintLayout_shadow_color,
                shadowColor
            )
            borderColor = it.getColor(
                R.styleable.ShadowConstraintLayout_border_color,
                borderColor
            )
            layoutBackgroundColor = it.getColor(
                R.styleable.ShadowConstraintLayout_card_background_color,
                layoutBackgroundColor
            )
            enableShadow =
                it.getBoolean(R.styleable.ShadowConstraintLayout_enable_shadow, enableShadow)
            enableBorder =
                it.getBoolean(R.styleable.ShadowConstraintLayout_enable_border, enableBorder)
            enableShadowTop =
                it.getBoolean(R.styleable.ShadowConstraintLayout_enable_shadow_top, enableShadowTop)
            enableShadowBottom =
                it.getBoolean(R.styleable.ShadowConstraintLayout_enable_shadow_bottom, enableShadowBottom)
            enableShadowStart =
                it.getBoolean(R.styleable.ShadowConstraintLayout_enable_shadow_start, enableShadowStart)
            enableShadowEnd =
                it.getBoolean(R.styleable.ShadowConstraintLayout_enable_shadow_end, enableShadowEnd)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateShadow()
        updateBorder()
        updateLayout()
        updateBackground()
    }

    override fun onDraw(canvas: Canvas) {
        if (enableShadow) {
            canvas.apply {
                drawPath(shadowTopPath, shadowPaint)
                drawPath(shadowBottomPath, shadowPaint)
                drawPath(shadowStartPath, shadowPaint)
                drawPath(shadowEndPath, shadowPaint)
            }
        }
        if (enableBorder)
            canvas.drawRoundRect(borderRectF, cornerRadius, cornerRadius, borderPaint)
        canvas.drawRoundRect(layoutBackgroundRectF, cornerRadius, cornerRadius, layoutPaint)

        super.onDraw(canvas)
    }

    private fun updateBackground() {
        background =
            MaterialShapeDrawable(ShapeAppearanceModel().withCornerSize(cornerRadius))
    }

    private fun updateShadow() {
        val useableWidth = width - (paddingLeft + paddingRight)
        val useableHeight = height - (paddingTop + paddingBottom)
        shadowPaint.apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            color = shadowColor
            strokeWidth = shadowStrokeWidth
            xfermode = porterDuffXferMode
            maskFilter = blurMaskFilter
        }
        if(enableShadowTop){
            shadowTopPath.apply {
                reset()
                moveTo((useableWidth + shadowEndOffset), shadowTopOffset)
                lineTo(shadowStartOffset, shadowTopOffset)
            }
        }

        if(enableShadowStart){
            shadowStartPath.apply {
                reset()
                moveTo(shadowStartOffset, shadowTopOffset)
                lineTo(shadowStartOffset, (useableHeight + shadowBottomOffset))
            }
        }

        if(enableShadowBottom){
            shadowBottomPath.apply {
                reset()
                moveTo(shadowStartOffset, (useableHeight + shadowBottomOffset))
                lineTo((useableWidth + shadowEndOffset), (useableHeight + shadowBottomOffset))
            }
        }

        if(enableShadowEnd){
            shadowEndPath.apply {
                reset()
                moveTo((useableWidth + shadowEndOffset), (useableHeight + shadowBottomOffset))
                lineTo((useableWidth + shadowEndOffset), shadowTopOffset)
            }
        }

        invalidate()
    }

    private fun updateBorder() {
        val useableWidth = width - (paddingLeft + paddingRight)
        val useableHeight = height - (paddingTop + paddingBottom)
        borderPaint.apply {
            style = Paint.Style.STROKE
            color = borderColor
            strokeWidth = 1.dpToPixelFloat
        }

        borderRectF.apply {
            top = 0f
            left = 0f
            right = useableWidth.toFloat()
            bottom = useableHeight.toFloat()
        }
        invalidate()
    }

    private fun updateLayout() {
        val useableWidth = width - (paddingLeft + paddingRight)
        val useableHeight = height - (paddingTop + paddingBottom)
        layoutPaint.apply {
            style = Paint.Style.FILL
            color = layoutBackgroundColor
            xfermode = porterDuffXferMode
        }
        layoutBackgroundRectF.apply {
            top = 0f
            left = 0f
            right = useableWidth.toFloat()
            bottom = useableHeight.toFloat()
        }
        layoutBackgroundPath.apply {
            reset()
            addRect(layoutBackgroundRectF, Path.Direction.CW)
        }

        invalidate()
    }
}