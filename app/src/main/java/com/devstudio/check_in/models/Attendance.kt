package com.devstudio.check_in.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Attendance(
    var _id: String ="",
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
    var hour:Int?=0,
    var minutes:Int?=0,
    var amOrPm:String?=""
):RealmObject()
