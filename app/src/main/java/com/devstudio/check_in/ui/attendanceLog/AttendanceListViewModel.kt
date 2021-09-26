package com.devstudio.check_in.ui.attendanceLog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devstudio.check_in.models.AttendanceDate
import com.devstudio.check_in.repository.AttendanceRepository

class AttendanceListViewModel : ViewModel() {
    private var attendanceRepository = AttendanceRepository()
    val selectedDate = MutableLiveData<AttendanceDate>()
}