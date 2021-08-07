package com.hks.kr.wifireminder.ui.calendar

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.hks.kr.wifireminder.databinding.FragmentCalendarBinding
import com.hks.kr.wifireminder.utils.delegate.FragmentBindingDelegate
import com.hks.kr.wifireminder.utils.resizeDialogSize

class CalendarDialogFragment : DialogFragment() {

    private var binding: FragmentCalendarBinding by FragmentBindingDelegate()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentCalendarBinding.inflate(layoutInflater, container, false)
        .also { fragmentCalendarBinding -> binding = fragmentCalendarBinding }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        resizeDialogSize(0.875f, 0.542f)
    }
}