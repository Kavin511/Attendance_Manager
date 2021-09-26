package com.devstudio.check_in.dao


import com.devstudio.check_in.models.Attendance
import com.devstudio.check_in.models.AttendanceDate
import com.devstudio.check_in.models.TimeModel
import com.devstudio.check_in.util.AppConstants.Companion.DATE
import com.devstudio.check_in.util.AppConstants.Companion.MONTH
import com.devstudio.check_in.util.AppConstants.Companion.TIME
import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.Sort
import io.realm.annotations.RealmClass


class AttendanceDao {
    private lateinit var realm: Realm
    fun allAttendance(date: AttendanceDate): OrderedRealmCollection<Attendance?> {
        realm = Realm.getDefaultInstance()
        return realm.where(Attendance::class.java)
            .equalTo(DATE, date.date)
            .equalTo(MONTH, date.month).and()
            .findAll().sort(TIME,Sort.DESCENDING)
    }

    fun saveAttendance(attendance: Attendance) {
        realm = Realm.getDefaultInstance()
        realm.executeTransaction { r: Realm ->
            val attendanceModel = realm.createObject(Attendance::class.java)
            val startTimeModel = realm.createObject(TimeModel::class.java)
            val endTimeModel = realm.createObject(TimeModel::class.java)
            startTimeModel.hour=attendance.startingTime!!.hour
            startTimeModel.minutes=attendance.startingTime!!.minutes
            startTimeModel.amOrPm=attendance.startingTime!!.amOrPm
            endTimeModel.hour=attendance.endingTime!!.hour
            endTimeModel.minutes=attendance.endingTime!!.minutes
            endTimeModel.amOrPm=attendance.endingTime!!.amOrPm
            attendanceModel.time = attendance.time
            attendanceModel.employeeName = attendance.employeeName
            attendanceModel.employeeSalary = attendance.employeeSalary
            attendanceModel.overTimeWorked = attendance.overTimeWorked
            attendanceModel.startingTime =startTimeModel
            attendanceModel.endingTime = endTimeModel
            attendanceModel.date=attendance.date
            attendanceModel.month=attendance.month
            attendanceModel.year=attendance.year
        }
    }

}



