package com.example.attendance_sheet.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.mongodb.Credentials
import java.util.*

open class Attendance(
    @PrimaryKey
    var _id: String = UUID.randomUUID().toString(),
    var _partition:String?=null,
    var time:Date?=null,
    var employeeName: String? = null,
    var employeeSalary: String? = null,
    var overTimeWorked: Boolean? = null,
    var startingTime:TimeModel?=null,
    var endingTime:TimeModel?=null,
    var date:Int?=null,
    var month:Int?=null,
    var year:Int?=null
):RealmObject()

open class TimeModel(
    var hour:Int?=null,
    var minutes:Int?=null,
    var amOrPm:String?=null
):RealmObject()
