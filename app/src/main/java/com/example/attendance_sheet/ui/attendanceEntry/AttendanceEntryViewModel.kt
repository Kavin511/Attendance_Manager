package com.example.attendance_sheet.ui.attendanceEntry

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.attendance_sheet.DB.Models.AttendanceModel
import io.realm.Realm
import java.lang.Exception


class AttendanceEntryViewModel : ViewModel() {
    private val attendanceModel = AttendanceModel()
    var labourName: String? = null
    var labourSalary: String? = null
    var overTimeWorked: Boolean = false
    var startingTime: Long? = null
    var endingTime: Long? = null
    var attendanceEntryListener: attendanceEntryInterface? = null
    fun onEntryClicked(view: View) {
        attendanceEntryListener?.onstarted()
        if (labourName.isNullOrEmpty() ||
            labourSalary.isNullOrEmpty()
        ) {
            attendanceEntryListener?.onFailure("Enter all the details")
        } else {

            Realm.getDefaultInstance().use { realm ->
//                if (realm.where(
//                        AttendanceModel::class.java
//                    ).equalTo(attendanceModel., id).count() <= 0L
//                    && realm.where(SubscriberObject::class.java)
//                        .equalTo(SubscriberObject.USERNAME, user).count() <= 0L
//                ) {
                try {
                    val attendanceModel = AttendanceModel()
                    attendanceModel.labourName = labourName
                    attendanceModel.labourSalary = labourSalary
                    attendanceModel.overTimeWorked = overTimeWorked
                    attendanceModel.startingTime = startingTime
                    attendanceModel.endingTime = endingTime

                    realm.executeTransaction { r: Realm ->
                        r.insert(
                            attendanceModel
                        )
                    }
                    realm.commitTransaction()
                }catch (e:Exception){
                    Log.e("error", "onEntryClicked: "+e, )

//                    }
//                    return true
                }
            }

        }
    }
}