package com.dev_studio.check_in.ui.attendanceLog

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.TimePicker
import androidx.recyclerview.widget.RecyclerView
import com.dev_studio.check_in.R
import com.dev_studio.check_in.models.Attendance
import com.dev_studio.check_in.models.TimeModel
import com.dev_studio.check_in.repository.AttendanceRepository
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.RealmRecyclerViewAdapter
import java.util.*


internal class AttendanceLogAdapter(data: OrderedRealmCollection<Attendance?>?) :
    RealmRecyclerViewAdapter<Attendance?,
            AttendanceLogAdapter.ViewHolder?>(data, true) {
    lateinit var context: Context
    private var attendanceRepository = AttendanceRepository()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val textView: View =
            inflater.inflate(com.dev_studio.check_in.R.layout.list_item, parent, false)
        return ViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val obj = getItem(position)
        holder.data = obj
        val v = holder.item
        val options = v.findViewById<TextView>(com.dev_studio.check_in.R.id.options)
        val labourName = v.findViewById<TextView>(com.dev_studio.check_in.R.id.labourName)
        val labourSalary = v.findViewById<TextView>(com.dev_studio.check_in.R.id.labourSalary)
        val startingTime = v.findViewById<TextView>(com.dev_studio.check_in.R.id.startingTime)
        val endingTime = v.findViewById<TextView>(com.dev_studio.check_in.R.id.endingTime)
        val overtTimeCheckBox =
            v.findViewById<TextView>(com.dev_studio.check_in.R.id.overtTimeCheckBox)
        val time = v.findViewById<TextView>(com.dev_studio.check_in.R.id.time)
        labourName.text = obj?.employeeName
        (obj?.employeeSalary + " ₹").also { labourSalary.text = it }
        startingTime.text = timeToString(obj?.startingTime)
        endingTime.text = timeToString(obj?.endingTime)
        if (obj?.overTimeWorked == true) {
            "Overtime worked".also { overtTimeCheckBox.text = it }
        } else {
            "Not overtime worked".also { overtTimeCheckBox.text = it }
        }
        time.text = obj?.time.toString()
        options.setOnClickListener { showPopup(options, obj) }
    }

    private fun showPopup(item: View, obj: Attendance?) {
        val popup = PopupMenu(context, item)
        popup.inflate(com.dev_studio.check_in.R.menu.log_options)
        popup.setOnMenuItemClickListener { itm ->
            when (itm.itemId) {
                com.dev_studio.check_in.R.id.delete -> {
                    MaterialAlertDialogBuilder(context)
                        .setMessage("Are you sure to delete this attendance log?")
                        .setTitle("Log Deletion")
                        .setNegativeButton("NO") { _, _ ->

                        }
                        .setPositiveButton("Yes") { dialog, which ->
                            deleteLog(obj)
                        }
                        .show()
                }
                R.id.edit -> {
                    val bottomSheetDialog = BottomSheetDialog(context)
                    bottomSheetDialog.setContentView(R.layout.fragment_attendance_entry)
                    val labourName =
                        bottomSheetDialog.findViewById<EditText>(R.id.labourName)
                    val labourSalary =
                        bottomSheetDialog.findViewById<EditText>(R.id.labourSalary)
                    val overTimeWorked =
                        bottomSheetDialog.findViewById<MaterialCheckBox>(R.id.overtTimeCheckBox)
                    val startingTime =
                        bottomSheetDialog.findViewById<TimePicker>(R.id.work_starting_time)
                    val endingTime =
                        bottomSheetDialog.findViewById<TimePicker>(R.id.work_ending_time)
                    val addAttendance =
                        bottomSheetDialog.findViewById<MaterialButton>(R.id.addAttendance)
                    labourName?.text?.append(obj?.employeeName)
                    labourSalary?.text?.append(obj?.employeeSalary)
                    overTimeWorked?.isChecked = obj?.overTimeWorked ?: false
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        startingTime?.hour = obj?.startingTime?.hour ?: 0
                        startingTime?.minute = obj?.startingTime?.minutes ?: 0
                        endingTime?.hour = obj?.endingTime?.hour ?: 0
                        endingTime?.minute = obj?.endingTime?.minutes ?: 0
                    } else {
                        startingTime?.currentHour = obj?.startingTime?.hour ?: 0
                        startingTime?.currentMinute = obj?.startingTime?.minutes ?: 0
                        endingTime?.currentHour = obj?.endingTime?.hour ?: 0
                        endingTime?.currentMinute = obj?.endingTime?.minutes ?: 0

                    }


                    addAttendance?.setOnClickListener {
                        val attendance = Attendance()
                        attendance._id = obj?._id ?: 0
                        attendance.time = Calendar.getInstance().time
                        attendance.employeeName = labourName?.text.toString()
                        attendance.employeeSalary = labourSalary?.text.toString()
                        attendance.overTimeWorked = overTimeWorked?.isChecked ?: false
                        attendance.startingTime = setTimeToTimePickers(startingTime!!)
                        attendance.endingTime = setTimeToTimePickers(endingTime!!)
                        val cal = Calendar.getInstance()
                        attendance.date = cal.get(Calendar.DATE)
                        attendance.month = cal.get(Calendar.MONTH)
                        attendance.year = cal.get(Calendar.YEAR)
                        attendanceRepository.updateAttendance(attendance)
                        bottomSheetDialog.dismiss()
                    }
                    bottomSheetDialog.show()

                }
            }
            false
        }
        popup.show()
    }

    fun setTimeToTimePickers(time: TimePicker): TimeModel {
        val timeSpec: TimeModel = TimeModel()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timeSpec.hour = time.hour
            timeSpec.minutes = time.minute
            timeSpec.amOrPm = if (time.hour >= 12) "PM" else "AM"
        } else {
            timeSpec.hour = time.currentHour
            timeSpec.minutes = time.currentMinute
            timeSpec.amOrPm = if (time.currentHour >= 12) "PM" else "AM"
        }
        return timeSpec
    }

    private fun attendanceDataUpdate(
        startTimeModel: TimeModel,
        attendance: Attendance,
        endTimeModel: TimeModel,
        attendanceModel: Attendance
    ) {
        startTimeModel.hour = attendance.startingTime!!.hour
        startTimeModel.minutes = attendance.startingTime!!.minutes
        startTimeModel.amOrPm = attendance.startingTime!!.amOrPm
        endTimeModel.hour = attendance.endingTime!!.hour
        endTimeModel.minutes = attendance.endingTime!!.minutes
        endTimeModel.amOrPm = attendance.endingTime!!.amOrPm
        attendanceModel.time = attendance.time
        attendanceModel.employeeName = attendance.employeeName
        attendanceModel.employeeSalary = attendance.employeeSalary
        attendanceModel.overTimeWorked = attendance.overTimeWorked
        attendanceModel.startingTime = startTimeModel
        attendanceModel.endingTime = endTimeModel
        attendanceModel.date = attendance.date
        attendanceModel.month = attendance.month
        attendanceModel.year = attendance.year
    }

    private fun deleteLog(obj: Attendance?) {
        Realm.getDefaultInstance().executeTransaction {
            obj?.deleteFromRealm()
        }

    }

    internal inner class ViewHolder(var item: View) : RecyclerView.ViewHolder(item) {
        var data: Attendance? = null
    }

    private fun timeToString(timeModel: TimeModel?): String {
        return "" + timeModel?.hour + " : " + timeModel?.minutes + " " + timeModel?.amOrPm
    }
}

