package com.example.attendance_sheet.ui.attendanceEntry

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.attendance_sheet.R
import com.example.attendance_sheet.util.toast
import com.example.attendance_sheet.databinding.FragmentAttendanceEntryBinding
import com.example.attendance_sheet.models.TimeModel
import com.example.attendance_sheet.viewModels.AttendanceViewModel
import com.example.attendance_sheet.ui.attendanceLog.AttendanceListViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Attendance : Fragment(), AttendanceInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    lateinit var labourStartingTime: TimePicker
    lateinit var labourEndingTime: TimePicker
    lateinit var viewModel: AttendanceViewModel
    lateinit var binding: FragmentAttendanceEntryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_attendance_entry, container, false)
        viewModel = ViewModelProviders.of(this).get(AttendanceViewModel::class.java)
        binding.attendanceViewModel = viewModel
        viewModel.attendanceListener = this
        labourStartingTime = binding.workStartingTime
        labourEndingTime = binding.workEndingTime
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTimeToTimePickers(labourEndingTime, viewModel.endingTime)
        setTimeToTimePickers(labourStartingTime, viewModel.startingTime)
        setTimeChangeListeners()
    }

    private fun setTimeChangeListeners() {
        labourEndingTime.setOnTimeChangedListener { picker, _, _ ->
            setTimeToTimePickers(
                picker,
                viewModel.endingTime
            )
        }
        labourStartingTime.setOnTimeChangedListener { picker, _, _ ->
            setTimeToTimePickers(
                picker,
                viewModel.startingTime
            )
        }
    }

    private fun setTimeToTimePickers(time: TimePicker, timeSpec: TimeModel) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timeSpec.hour = time.hour
            timeSpec.minutes = time.minute
            timeSpec.amOrPm = if (time.hour >= 12) "PM" else "AM"
        } else {
            timeSpec.hour = time.currentHour
            timeSpec.minutes = time.currentMinute
            timeSpec.amOrPm = if (time.currentHour >= 12) "PM" else "AM"
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Attendance().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onError(message: String) {
        context?.toast(message)
    }

    override fun onStarted() {
        context?.toast("Posting Attendance!")
    }

    override fun onFailure(message: String) {
        context?.toast(message)
    }

    override fun onSuccess() {
        context?.toast("Attendance posted successfully!")
    }
}
