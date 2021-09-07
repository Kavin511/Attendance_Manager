package com.example.attendance_sheet.ui.attendanceLog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.lifecycle.ViewModelProviders
import com.example.attendance_sheet.R
import com.example.attendance_sheet.models.AttendanceDate
import com.example.attendance_sheet.viewModels.AttendanceViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CalendarDialog.newInstance] factory method to
 * create an instance of this fragment.
 */
class CalendarDialog : BottomSheetDialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    lateinit var viewModel: AttendanceViewModel
    lateinit var calendar:CalendarView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v= inflater.inflate(R.layout.fragment_calendar_dialog, container, false)
        viewModel = ViewModelProviders.of(this).get(AttendanceViewModel::class.java)
        calendar=v.findViewById(R.id.calendar)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendar.setOnDateChangeListener { _, year, month, date ->
            viewModel.fetchAttendance(AttendanceDate(date, month, year))
            dismiss()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CalendarDialog().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}