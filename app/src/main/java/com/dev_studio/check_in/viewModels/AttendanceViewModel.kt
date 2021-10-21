package com.dev_studio.check_in.viewModels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev_studio.check_in.models.Attendance
import com.dev_studio.check_in.models.AttendanceDate
import com.dev_studio.check_in.models.TimeModel
import com.dev_studio.check_in.repository.AttendanceRepository
import com.dev_studio.check_in.ui.CompletionHandler
import com.dev_studio.check_in.ui.attendanceLog.AttendanceLogAdapter
import io.realm.OrderedRealmCollection
import io.realm.Realm
import java.util.*


class AttendanceViewModel : ViewModel() {
    var labourName: MutableLiveData<String> = MutableLiveData()
    var labourSalary: MutableLiveData<String> = MutableLiveData()
    var overTimeWorked: MutableLiveData<Boolean> = MutableLiveData()
    var startingTime: TimeModel = TimeModel(0, 0, "am")
    var endingTime: TimeModel = TimeModel(0, 0, "am")
    var errorName: String? = ""
    var isHavingRecords: Boolean = false
    var attendanceListener: CompletionHandler? = null
    var time: Date? = null
    private var attendanceRepository = AttendanceRepository()
    var selectedDate: MutableLiveData<AttendanceDate> = MutableLiveData()
    var results: MutableLiveData<OrderedRealmCollection<Attendance?>?> = MutableLiveData()
    internal var adapter: AttendanceLogAdapter? = null

    init {
        selectedDate.value = initializeDate()
        results.value = fetchAttendance(selectedDate.value!!).value
    }

    fun onEntryClicked(view: View) {
        if (labourName.value?.length ?: "".length < 3) {
            errorName = "Enter at least 3 characters in labour name"
            attendanceListener?.onFailure(errorName!!)
        } else if (labourSalary.value == "0" || labourSalary.value?.isEmpty() != false) {
            errorName = "Enter valid salary"
            attendanceListener?.onFailure(errorName!!)
        } else {
            Realm.getDefaultInstance().use {
                try {
                    val attendanceModel = Attendance()
                    updateAttendanceObject(attendanceModel)
                    attendanceRepository.saveAttendance(attendanceModel)
                    attendanceListener?.onSuccess("Attendance Posted")
                    selectedDate.value?.let { fetchAttendance(it) }
                } catch (e: Exception) {
                    attendanceListener?.onFailure(e.toString())
                }
            }
        }
    }

    private fun updateAttendanceObject(attendanceModel: Attendance) {
        attendanceModel._id = Calendar.getInstance().timeInMillis
        attendanceModel.time = Calendar.getInstance().time
        attendanceModel.employeeName = labourName.value.toString()
        attendanceModel.employeeSalary = labourSalary.value.toString()
        attendanceModel.overTimeWorked = overTimeWorked.value ?: false
        attendanceModel.startingTime = startingTime
        attendanceModel.endingTime = endingTime
        val cal = Calendar.getInstance()
        attendanceModel.date = cal.get(Calendar.DATE)
        attendanceModel.month = cal.get(Calendar.MONTH)
        attendanceModel.year = cal.get(Calendar.YEAR)
    }

    private fun initializeDate(): AttendanceDate {
        val cal = Calendar.getInstance()
        return AttendanceDate(
            cal.get(Calendar.DATE),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.YEAR)
        )

    }

    fun fetchAttendance(date: AttendanceDate): MutableLiveData<OrderedRealmCollection<Attendance?>?> {
        return try {
            results.value = attendanceRepository.allAttendance(date)
            results
        } catch (e: Exception) {
            results
        }
    }

}
