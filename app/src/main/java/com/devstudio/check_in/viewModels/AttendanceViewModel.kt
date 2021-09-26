package com.devstudio.check_in.viewModels

import android.view.View
import androidx.lifecycle.*
import com.devstudio.check_in.models.AttendanceDate
import com.devstudio.check_in.models.Attendance
import com.devstudio.check_in.models.TimeModel
import com.devstudio.check_in.repository.AttendanceRepository
import com.devstudio.check_in.ui.CompletionHandler
import com.devstudio.check_in.ui.attendanceLog.AttendanceLogAdapter
import io.realm.OrderedRealmCollection
import io.realm.Realm
import java.util.*


class AttendanceViewModel : ViewModel() {
    var labourName: String = ""
    var labourSalary: String = ""
    var overTimeWorked: Boolean = false
    var startingTime: TimeModel = TimeModel(0, 0, "am")
    var endingTime: TimeModel = TimeModel(0, 0, "am")
    var errorName: String? = ""
    var attendanceListener: CompletionHandler? = null
    var time: Date? = null
    private var attendanceRepository = AttendanceRepository()
    var selectedDate: MutableLiveData<AttendanceDate> = MutableLiveData()
    var results: MutableLiveData<OrderedRealmCollection<Attendance?>?> = MutableLiveData()
    internal var adapter: AttendanceLogAdapter? =null
    init {
        selectedDate.value=initializeDate()
        results.value=fetchAttendance(selectedDate.value!!).value
    }
    fun onEntryClicked(view: View) {
        if (labourName.length < 3) {
            errorName = "Enter at least 3 characters in labour name"
            attendanceListener?.onFailure(errorName!!)
        } else if (labourSalary == "0" || labourSalary.isEmpty()) {
            errorName = "Enter valid salary"
            attendanceListener?.onFailure(errorName!!)
        } else {
            Realm.getDefaultInstance().use { realm ->
                try {
                    val attendanceModel = Attendance()
                    attendanceModel._id= UUID.randomUUID().toString()
                    attendanceModel.time = Calendar.getInstance().time
                    attendanceModel.employeeName = labourName
                    attendanceModel.employeeSalary = labourSalary
                    attendanceModel.overTimeWorked = overTimeWorked
                    attendanceModel.startingTime = startingTime
                    attendanceModel.endingTime = endingTime
                    val cal=Calendar.getInstance()
                    attendanceModel.date = cal.get(Calendar.DATE)
                    attendanceModel.month =  cal.get(Calendar.MONTH)
                    attendanceModel.year = cal.get(Calendar.YEAR)
                    attendanceRepository.saveAttendance(attendanceModel)
                    attendanceListener?.onSuccess("Attendance Posted")
                    selectedDate.value?.let { fetchAttendance(it) }
                } catch (e: Exception) {
                    attendanceListener?.onFailure(e.toString())
                }
            }
        }
    }

    private fun initializeDate(): AttendanceDate {
        val cal=Calendar.getInstance()
      return AttendanceDate(cal.get(Calendar.DATE),cal.get(Calendar.MONTH),cal.get(Calendar.YEAR))

    }

    fun fetchAttendance(date: AttendanceDate): MutableLiveData<OrderedRealmCollection<Attendance?>?> {
      return   try {
            results.value = attendanceRepository.allAttendance(date)
          results
        } catch (e: Exception) {
            results
        }
    }

}
