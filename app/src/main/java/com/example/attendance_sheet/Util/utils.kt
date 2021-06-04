package com.example.attendance_sheet.Util

import android.content.Context
import android.widget.Toast

fun Context.toast(message:String)
{
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}