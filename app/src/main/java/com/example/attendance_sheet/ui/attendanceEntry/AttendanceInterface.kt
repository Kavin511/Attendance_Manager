package com.example.attendance_sheet.ui.attendanceEntry

interface AttendanceInterface {
    fun onError(message: String)
    fun onStarted()
    fun onFailure(message:String)
    fun onSuccess()
}