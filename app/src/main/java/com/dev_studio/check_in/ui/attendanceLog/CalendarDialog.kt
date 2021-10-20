package com.dev_studio.check_in.ui.attendanceLog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.lifecycle.ViewModelProviders
import com.dev_studio.check_in.R
import com.dev_studio.check_in.models.AttendanceDate
import com.dev_studio.check_in.viewModels.AttendanceViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
class CalendarDialog : BottomSheetDialogFragment() {
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
}