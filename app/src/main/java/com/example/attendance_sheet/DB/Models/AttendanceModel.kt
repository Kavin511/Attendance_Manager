package com.example.attendance_sheet.DB.Models

import io.realm.RealmObject

open class AttendanceModel(
//    var time: Long? = null,
    var labourName: String? = null,
    var labourSalary: String? = null,
    var startingTime: Long? = null,
    var endingTime: Long? = null,
    var overTimeWorked: Boolean = false,
):RealmObject()
