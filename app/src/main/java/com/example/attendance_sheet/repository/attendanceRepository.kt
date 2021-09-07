package com.example.attendance_sheet.repository

import com.example.attendance_sheet.dao.AttendanceDao
import com.example.attendance_sheet.models.AttendanceDate
import com.example.attendance_sheet.models.Attendance
import io.realm.OrderedRealmCollection

class  AttendanceRepository {
    private val attendanceDao=AttendanceDao()
    fun allAttendance(date:AttendanceDate): OrderedRealmCollection<Attendance?>?{
        return attendanceDao.allAttendance(date)
    }
    fun saveAttendance(attendanceModel: Attendance) {
        attendanceDao.saveAttendance(attendanceModel)
    }
}
