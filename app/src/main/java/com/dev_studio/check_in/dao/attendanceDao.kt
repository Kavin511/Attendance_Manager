package com.dev_studio.check_in.dao


import com.dev_studio.check_in.models.Attendance
import com.dev_studio.check_in.models.AttendanceDate
import com.dev_studio.check_in.models.TimeModel
import com.dev_studio.check_in.util.AppConstants.Companion.DATE
import com.dev_studio.check_in.util.AppConstants.Companion.MONTH
import com.dev_studio.check_in.util.AppConstants.Companion.TIME
import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.Sort


class AttendanceDao {

    fun allAttendance(date: AttendanceDate): OrderedRealmCollection<Attendance?> {
        return realm().where(Attendance::class.java)
            .equalTo(DATE, date.date).and()
            .equalTo(MONTH, date.month).and()
            .findAll().sort(TIME, Sort.DESCENDING)
    }

    fun saveAttendance(attendance: Attendance) {

        realm().executeTransaction { r: Realm ->
            attendanceDataUpdate(attendance)
        }
    }

    private fun realm(): Realm {
        return Realm.getDefaultInstance()
    }

    fun updateAttendance(attendance: Attendance) {
        realm().beginTransaction()
        realm().copyToRealmOrUpdate(attendance)
        realm().commitTransaction()
    }

    private fun attendanceDataUpdate(
        attendance: Attendance
    ) {
        val attendanceModel = realm().createObject(Attendance::class.java, attendance._id)
        val startTimeModel = realm().createObject(TimeModel::class.java)
        val endTimeModel = realm().createObject(TimeModel::class.java)
        startTimeModel.hour = attendance.startingTime!!.hour
        startTimeModel.minutes = attendance.startingTime!!.minutes
        startTimeModel.amOrPm = attendance.startingTime!!.amOrPm
        endTimeModel.hour = attendance.endingTime!!.hour
        endTimeModel.minutes = attendance.endingTime!!.minutes
        endTimeModel.amOrPm = attendance.endingTime!!.amOrPm
        attendanceModel.time = attendance.time
        attendanceModel.employeeName = attendance.employeeName
        attendanceModel.employeeSalary = attendance.employeeSalary
        attendanceModel.overTimeWorked = attendance.overTimeWorked
        attendanceModel.startingTime = startTimeModel
        attendanceModel.endingTime = endTimeModel
        attendanceModel.date = attendance.date
        attendanceModel.month = attendance.month
        attendanceModel.year = attendance.year
    }

}



