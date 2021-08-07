package com.hks.kr.wifireminder.ui.addTask

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.hks.kr.wifireminder.databinding.FragmentAddTaskBinding
import com.hks.kr.wifireminder.ui.calendar.CalendarDialogFragment
import com.hks.kr.wifireminder.utils.delegate.FragmentBindingDelegate
import com.hks.kr.wifireminder.utils.resizeDialogSize
import com.hks.kr.wifireminder.utils.shortToast
import dagger.hilt.android.AndroidEntryPoint

/**
 * DialogFragment가 아니라 그냥 Fragment로 해야할 거 같음
 */
@AndroidEntryPoint
class AddTaskDialogFragment : DialogFragment() {

    private var binding: FragmentAddTaskBinding by FragmentBindingDelegate()

    private val viewModel: AddTaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAddTaskBinding.inflate(layoutInflater, container, false)
        .also { fragmentAddTaskBinding -> binding = fragmentAddTaskBinding }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.bindingViewModel = viewModel

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        resizeDialogSize(0.875f, 0.542f)
        // category를 처음 설정한다면 이는
        binding.addTaskCategorySpinner.apply {
            dataSet = arrayOf("hello", "Lets", "See", "The", "Magic", "카테고리를 늘리고 싶으시면 이쪽을 클릭해주세요")
            selectionChanged { _, itemText ->
                requireContext().shortToast(itemText)
            }
        }
        binding.addTaskTaskImportanceSpinner.apply {
            dataSet = arrayOf("High", "Medium", "Low")
            selectionChanged { _, itemText ->
                requireContext().shortToast(itemText)
            }
        }

        binding.btnSelectDate.setOnClickListener {
            CalendarDialogFragment().show(childFragmentManager, CALENDAR_DIALOG_FRAGMENT_TAG)
        }
    }

    companion object {
        private const val CALENDAR_DIALOG_FRAGMENT_TAG = "CalendarDialogFragment"
    }
}