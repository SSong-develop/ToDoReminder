package com.hks.kr.wifireminder.view.customizableSpinner

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.content.res.ResourcesCompat
import com.hks.kr.wifireminder.R
import com.hks.kr.wifireminder.utils.OnChangeProp
import com.hks.kr.wifireminder.utils.dpToPixel

/**
 * in xml attribute must set app:drop_down_padding value 1dp!!!!
 */
class CustomizableSpinner : AppCompatSpinner {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        // context , attrs 를 받는 생성자를 호출 , 즉 xml상에서 이 CustomizableSpinner가 호출됐을 때 typedArray를 initialize한다.
        attrs.let {
            typedArray = context.obtainStyledAttributes(it, R.styleable.CustomizableSpinner, 0, 0)
        }
    }

    // Local Variables
    // 선택이 바뀌는 함수
    private var selectionChanged: ((Int, String) -> Unit)? = null

    // xml attribute를 위한 typeArray
    private var typedArray: TypedArray? = null

    // Custom adapter initialized with empty array
    // spinner를 위한 adapter
    private val dataAdapter = CustomizableDropdownAdapter(context, arrayOf(), dropDownView = {
        return@CustomizableDropdownAdapter getDropDownView(it)
    }, selectedView = {
        return@CustomizableDropdownAdapter getSelectedView(it)
    })

    // spinner DataSet submit
    // 이건 delegate Pattern으로 변경
    var dataSet by OnChangeProp(arrayOf<String>()) {
        submitList(it)
    }

    init {
        // set adapter
        this.adapter = dataAdapter
        this.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // selectionChanged 함수
                // 내 앱에서는 포지션은 필요하진 않을거 같은데
                selectionChanged?.invoke(position, dataSet[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // empty
            }
        }
    }

    /**
     * submit list
     */
    private fun submitList(value: Array<String>) {
        dataAdapter.apply {
            objects = value
            notifyDataSetChanged()
        }
    }

    /**
     * customize view from attributes provided in xml
     */
    private fun getSelectedView(text: String): TextView {
        val textView = LayoutInflater.from(context)
            .inflate(android.R.layout.simple_spinner_item, null) as TextView
        textView.text = text
        textView.apply {
            includeFontPadding = false
        }

        for (i in 0..typedArray?.indexCount!!) {
            val attr = typedArray?.getIndex(i)
            when (attr) {
                R.styleable.CustomizableSpinner_selected_text_color ->
                    textView.setTextColor(typedArray?.getColor(attr, 0)!!)

                R.styleable.CustomizableSpinner_selected_background_color ->
                    textView.setBackgroundColor(typedArray?.getColor(attr, 0)!!)

                R.styleable.CustomizableSpinner_selected_text_size ->
                    textView.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX,
                        typedArray?.getDimensionPixelSize(attr, 0)!!.toFloat()
                    )

                R.styleable.CustomizableSpinner_selected_max_lines ->
                    textView.maxLines = typedArray?.getInt(attr, 1)!!

                R.styleable.CustomizableSpinner_selected_height ->
                    textView.height = typedArray?.getDimensionPixelOffset(attr, 1)!!

                R.styleable.CustomizableSpinner_selected_padding -> {
                    val padding = typedArray?.getDimensionPixelOffset(attr, 1)!!
                    textView.setPadding(padding, padding, padding, padding)
                }
                R.styleable.CustomizableSpinner_selected_text_font -> {
                    val fontId = typedArray?.getResourceId(
                        R.styleable.CustomizableSpinner_selected_text_font,
                        -1
                    )
                    val typeface = fontId?.let { ResourcesCompat.getFont(context, it) }
                    textView.typeface = typeface
                }
            }
        }
        return textView
    }

    /**
     * customize dropdown view from attributes provided in xml
     */
    private fun getDropDownView(text: String): TextView {
        val textView = LayoutInflater.from(context)
            .inflate(android.R.layout.simple_spinner_dropdown_item, null) as TextView
        textView.text = text
        textView.apply {
            includeFontPadding = false
        }

        for (i in 0..typedArray?.indexCount!!) {
            val attr = typedArray?.getIndex(i)
            when (attr) {
                R.styleable.CustomizableSpinner_drop_down_text_color ->
                    textView.setTextColor(typedArray?.getColor(attr, 0)!!)

                R.styleable.CustomizableSpinner_drop_down_background_color ->
                    textView.setBackgroundColor(typedArray?.getColor(attr, 0)!!)

                R.styleable.CustomizableSpinner_drop_down_text_size ->
                    textView.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX,
                        typedArray?.getDimensionPixelSize(attr, 0)!!.toFloat()
                    )

                R.styleable.CustomizableSpinner_drop_down_max_lines ->
                    textView.maxLines = typedArray?.getInt(attr, 1)!!

                R.styleable.CustomizableSpinner_drop_down_height ->
                    textView.height = typedArray?.getDimensionPixelOffset(attr, 50.dpToPixel)!!

                R.styleable.CustomizableSpinner_drop_down_padding -> {
                    val padding = typedArray?.getDimensionPixelOffset(attr, 1.dpToPixel)!!
                    textView.setPadding(padding, padding, padding, padding)
                }
                R.styleable.CustomizableSpinner_drop_down_text_font -> {
                    val fontId = typedArray?.getResourceId(
                        R.styleable.CustomizableSpinner_drop_down_text_font,
                        -1
                    )
                    val typeface = fontId?.let { ResourcesCompat.getFont(context, it) }
                    textView.typeface = typeface
                }
            }
        }
        return textView
    }

    /**
     *  SetUp selection changed callback
     */
    fun selectionChanged(selectionChanged: ((Int, String) -> Unit)?) {
        this.selectionChanged = selectionChanged
    }

    /**
     * Set selected value
     */
    fun setSelectedValue(value: String, animate: Boolean = false) {
        if (dataSet.contains(value)) this.setSelection(dataSet.indexOf(value), animate)
    }
}