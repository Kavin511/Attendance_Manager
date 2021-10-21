package com.dev_studio.check_in.repository

import com.dev_studio.check_in.dao.AttendanceDao
import com.dev_studio.check_in.models.AttendanceDate
import com.dev_studio.check_in.models.Attendance
import io.realm.OrderedRealmCollection

class AttendanceRepository {
    private val attendanceDao = AttendanceDao()
    fun allAttendance(date: AttendanceDate): OrderedRealmCollection<Attendance?> {
        return attendanceDao.allAttendance(date)
    }

    fun saveAttendance(attendanceModel: Attendance) {
        attendanceDao.saveAttendance(attendanceModel)
    }
    fun updateAttendance(attendanceModel: Attendance){
        attendanceDao.updateAttendance(attendanceModel)
    }
}
