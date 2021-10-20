package com.dev_studio.check_in.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Attendance(
    @PrimaryKey
    var _id: Long = 0,
    var time: Date? = null,
    var employeeName: String = "",
    var employeeSalary: String = "",
    var overTimeWorked: Boolean = false,
    var startingTime: TimeModel? = null,
    var endingTime: TimeModel? = null,
    var date: Int = 0,
    var month: Int = 0,
    var year: Int = 0
) : RealmObject()


open class TimeModel(
    var hour: Int = 0,
    var minutes: Int = 0,
    var amOrPm: String = ""
) : RealmObject()
