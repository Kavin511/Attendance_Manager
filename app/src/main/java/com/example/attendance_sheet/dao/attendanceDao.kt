package com.example.attendance_sheet.dao


import com.example.attendance_sheet.models.AttendanceDate
import com.example.attendance_sheet.models.Attendance
import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.createObject

class AttendanceDao {
    private lateinit var realm: Realm
    fun allAttendance(date: AttendanceDate): OrderedRealmCollection<Attendance?> {
        realm = Realm.getDefaultInstance()
        return realm.where(Attendance::class.java)
            .equalTo("date", date.date)
            .equalTo("month", date.month).and()
            .findAll().sort("time",Sort.DESCENDING)
    }

    fun saveAttendance(attendance: Attendance) {
        realm = Realm.getDefaultInstance()
        realm.executeTransaction { r: Realm ->
            val attendanceModel = realm.createObject<Attendance>()
            attendanceModel.time = attendance.time
            attendanceModel.employeeName = attendance.employeeName
            attendanceModel.employeeSalary = attendance.employeeSalary
            attendanceModel.overTimeWorked = attendance.overTimeWorked
            attendanceModel.startingTime = attendance.startingTime
            attendanceModel.endingTime = attendance.endingTime
            attendanceModel.date=attendance.date
            attendanceModel.month=attendance.month
            attendanceModel.year=attendance.year
            attendanceModel._partition="myPartition"


        }
    }

}


