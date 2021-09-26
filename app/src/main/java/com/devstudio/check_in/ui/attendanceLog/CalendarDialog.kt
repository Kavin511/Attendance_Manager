package com.devstudio.check_in.ui.attendanceLog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.lifecycle.ViewModelProviders
import com.devstudio.check_in.R
import com.devstudio.check_in.models.AttendanceDate
import com.devstudio.check_in.viewModels.AttendanceViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
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
}