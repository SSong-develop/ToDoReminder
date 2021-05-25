package com.hks.kr.wifireminder.view.customizableSpinner

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSpinner
import com.hks.kr.wifireminder.R
import com.hks.kr.wifireminder.utils.dpToPixel

/**
 * in xml attribute must set app:drop_down_padding value 1dp!!!!
 */
class CustomizableSpinner : AppCompatSpinner {
    constructor(context : Context) : super(context)
    constructor(context : Context , attrs : AttributeSet) : super(context,attrs) {
        attrs.let {
            typedArray = context.obtainStyledAttributes(it, R.styleable.CustomizableSpinner,0,0)
        }
    }

    // Local Variables
    private var selectionChanged: ((Int, String) -> Unit)? = null
    private var defaultColor = Color.BLACK
    private var typedArray: TypedArray? = null

    // Custom adapter initialized with empty array
    private val dataAdapter = CustomizableDropdownAdapter(context, arrayOf(), dropDownView = {
        return@CustomizableDropdownAdapter getDropDownView(it)
    }, selectedView = {
        return@CustomizableDropdownAdapter getSelectedView(it)
    })

    var dataSet = arrayOf<String>()
        set(value) {
            dataAdapter.objects = value
            dataAdapter.notifyDataSetChanged()
            field = value
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
                selectionChanged?.invoke(position, dataSet[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // empty
            }
        }
    }

    /**
     * customize view from attributes provided in xml
     */
    private fun getSelectedView(text: String): TextView {
        val textView = LayoutInflater.from(context)
            .inflate(android.R.layout.simple_spinner_item, null) as TextView
        textView.text = text

        for(i in 0..typedArray?.indexCount!!){
            val attr = typedArray?.getIndex(i)
            when(attr){
                R.styleable.CustomizableSpinner_selected_text_color ->
                    textView.setTextColor(typedArray?.getColor(attr,0)!!)

                R.styleable.CustomizableSpinner_selected_background_color ->
                    textView.setBackgroundColor(typedArray?.getColor(attr,0)!!)

                R.styleable.CustomizableSpinner_selected_text_size ->
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,typedArray?.getDimensionPixelSize(attr,0)!!.toFloat())

                R.styleable.CustomizableSpinner_selected_max_lines ->
                    textView.maxLines = typedArray?.getInt(attr,1)!!

                R.styleable.CustomizableSpinner_selected_height ->
                    textView.height = typedArray?.getDimensionPixelOffset(attr,1)!!

                R.styleable.CustomizableSpinner_selected_padding -> {
                    val padding = typedArray?.getDimensionPixelOffset(attr,1)!!
                    textView.setPadding(padding,padding,padding,padding)
                }
            }
        }
        return textView
    }

    /**
     * customize dropdown view from attributes provided in xml
     */
    private fun getDropDownView(text : String) : TextView {
        val textView = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item,null) as TextView
        textView.text = text

        for(i in 0..typedArray?.indexCount!!){
            val attr = typedArray?.getIndex(i)
            when(attr){
                R.styleable.CustomizableSpinner_drop_down_text_color ->
                    textView.setTextColor(typedArray?.getColor(attr,0)!!)

                R.styleable.CustomizableSpinner_drop_down_background_color ->
                    textView.setBackgroundColor(typedArray?.getColor(attr,0)!!)

                R.styleable.CustomizableSpinner_drop_down_text_size ->
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,typedArray?.getDimensionPixelSize(attr,0)!!.toFloat())

                R.styleable.CustomizableSpinner_drop_down_max_lines ->
                    textView.maxLines = typedArray?.getInt(attr,1)!!

                R.styleable.CustomizableSpinner_drop_down_height ->
                    textView.height = typedArray?.getDimensionPixelOffset(attr,50.dpToPixel)!!

                R.styleable.CustomizableSpinner_drop_down_padding -> {
                    val padding = typedArray?.getDimensionPixelOffset(attr,1.dpToPixel)!!
                    textView.setPadding(padding,padding,padding,padding)
                }
            }
        }
        return textView
    }

    /**
     *  SetUp selection changed callback
     */
    fun selectionChanged(selectionChanged : ((Int,String) -> Unit)?) {
        this.selectionChanged = selectionChanged
    }

    /**
     * Set selected value
     */
    fun setSelectedValue(value : String , animate : Boolean = false){
        if(dataSet.contains(value)) this.setSelection(dataSet.indexOf(value),animate)
    }
}