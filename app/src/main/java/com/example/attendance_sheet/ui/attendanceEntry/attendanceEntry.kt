package com.example.attendance_sheet.ui.attendanceEntry

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TimePicker
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.attendance_sheet.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import io.realm.Realm
import java.util.*
import com.example.attendance_sheet.Util.toast
import com.example.attendance_sheet.databinding.FragmentAttendanceEntryBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [attendanceEntry.newInstance] factory method to
 * create an instance of this fragment.
 */
class attendanceEntry : Fragment(), attendanceEntryInterface {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    lateinit var labourName: TextInputEditText
    lateinit var workStartingTime: TimePicker
    lateinit var workEndingTime: TimePicker
    lateinit var overtTimeCheckBox: CheckBox
    lateinit var labourSalary: TextInputEditText
    lateinit var addAttendance: MaterialButton
    lateinit var realm: Realm
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentAttendanceEntryBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_attendance_entry, container, false)
        val viewModel = ViewModelProviders.of(this).get(AttendanceEntryViewModel::class.java)
//        Realm.init(context)
        binding.attendanceEntryViewModel=viewModel
        viewModel.attendanceEntryListener=this
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment attendanceEntry.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            attendanceEntry().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    override fun onstarted() {
        context?.toast("Adding attendance")
    }

    override fun onFailure(message: String) {
        context?.toast(message)
    }

    override fun onSuccess() {
        context?.toast("Attendance added successfully")
    }
}
