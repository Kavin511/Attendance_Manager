package com.devstudio.check_in.ui.attendanceLog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.devstudio.check_in.R
import com.devstudio.check_in.models.Attendance
import com.devstudio.check_in.models.TimeModel
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter


internal class AttendanceLogAdapter(data: OrderedRealmCollection<Attendance?>?) :
    RealmRecyclerViewAdapter<Attendance?,
            AttendanceLogAdapter.ViewHolder?>(data, true) {
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val context: Context = parent.context
        val inflater = LayoutInflater.from(context)
        val textView: View = inflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val obj = getItem(position)
        holder.data = obj
        val v = holder.item
        val labourName = v.findViewById<TextView>(R.id.labourName)
        val labourSalary = v.findViewById<TextView>(R.id.labourSalary)
        val startingTime = v.findViewById<TextView>(R.id.startingTime)
        val endingTime = v.findViewById<TextView>(R.id.endingTime)
        val overtTimeCheckBox = v.findViewById<TextView>(R.id.overtTimeCheckBox)
        val time = v.findViewById<TextView>(R.id.time)
        labourName.text = obj?.employeeName
        (obj?.employeeSalary + " ₹").also { labourSalary.text = it }
        startingTime.text = timeToString(obj?.startingTime)
        endingTime.text = timeToString(obj?.endingTime)
        if (obj?.overTimeWorked == true) {
            "Overtime worked".also { overtTimeCheckBox.text = it }
        } else {
            "Not overtime worked".also { overtTimeCheckBox.text = it }}
        time.text = obj?.time.toString()
    }

    internal inner class ViewHolder(var item: View)
        : RecyclerView.ViewHolder(item) {
        var data: Attendance? = null
    }

    private fun timeToString(timeModel: TimeModel?):String{
        return   ""+timeModel?.hour+" : "+ timeModel?.minutes+ " "+timeModel?.amOrPm
    }
}

