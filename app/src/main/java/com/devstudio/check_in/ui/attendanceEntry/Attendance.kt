package com.devstudio.check_in.ui.attendanceEntry

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.devstudio.check_in.R
import com.devstudio.check_in.util.toast
import com.devstudio.check_in.databinding.FragmentAttendanceEntryBinding
import com.devstudio.check_in.models.TimeModel
import com.devstudio.check_in.ui.CompletionHandler
import com.devstudio.check_in.viewModels.AttendanceViewModel

class Attendance : Fragment(), CompletionHandler {

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
        const val POSTING_ATTENDANCE="Posting Attendance!"
        const val ATTENDANCE_POSTED="Attendance posted successfully!"
    }

    override fun onFailure(message: String) {
        context?.toast(message)
    }

    override fun onSuccess(message: String) {
        context?.toast(ATTENDANCE_POSTED)
    }
}
