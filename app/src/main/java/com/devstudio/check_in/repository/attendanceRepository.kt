package com.devstudio.check_in.repository

import com.devstudio.check_in.dao.AttendanceDao
import com.devstudio.check_in.models.AttendanceDate
import com.devstudio.check_in.models.Attendance
import io.realm.OrderedRealmCollection

class  AttendanceRepository {
    private val attendanceDao=AttendanceDao()
    fun allAttendance(date:AttendanceDate): OrderedRealmCollection<Attendance?>{
        return attendanceDao.allAttendance(date)
    }
    fun saveAttendance(attendanceModel: Attendance) {
        attendanceDao.saveAttendance(attendanceModel)
    }
}
