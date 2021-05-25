package com.hks.kr.wifireminder.view.customizableSpinner

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CustomizableDropdownAdapter(
    context: Context,
    var objects: Array<String>, // 스피너로 보여질 내용
    val dropDownView: (String) -> TextView, // 내려지면서 보여질 뷰
    val selectedView: (String) -> TextView
) : ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, objects) {

    // Request for customized view from the spinner
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return dropDownView(objects[position])
    }

    // Request for customized view from the spinner
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return selectedView(objects[position])
    }

    override fun getCount(): Int {
        return objects.count()
    }
}