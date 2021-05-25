package com.hks.kr.wifireminder.view.customizableSpinner

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

/**
 * 기존에 spinner를 만드는 방법을 생각해봐라
 * spinner를 xml 상에 배치시킨 후
 * ArrayListAdapter에 요소들을 넣어준다음
 * context, resource , data 를 선언해 adapter를 생성해준다.
 * 이후 특정 뷰를 선언해준다던지 , 혹은 listener를 달아준다던지 하는데
 *
 * 이걸 한번에 해줄 수 있다고 생각하는 것이다
 * 어떻게???
 * dataSet 즉 array와 , 선택시 보여질 뷰 , 펼쳐졌을 떄 보여질 뷰를 한번에 받을 수 있는 adapter를 생성하면 된다.
 */
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