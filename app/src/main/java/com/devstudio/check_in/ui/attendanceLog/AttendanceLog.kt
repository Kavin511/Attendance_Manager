package com.devstudio.check_in.ui.attendanceLog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.devstudio.check_in.R
import com.devstudio.check_in.databinding.FragmentAttendanceLogBinding
import com.devstudio.check_in.viewModels.AttendanceViewModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class AttendanceLog : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    lateinit var calendarButton: ExtendedFloatingActionButton
    lateinit var binding: FragmentAttendanceLogBinding
    lateinit var viewModel: AttendanceViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var noDataLayout: LinearLayout
    lateinit var swipe:SwipeRefreshLayout
    lateinit var calendarDialog:CalendarDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_attendance_log, container, false)
        viewModel = ViewModelProviders.of(this).get(AttendanceViewModel::class.java)
        binding.attendanceViewModel = viewModel
        initializeViews()
        observeLog()
        return binding.root
    }

    private fun initializeViews() {
        calendarButton = binding.calendar
        recyclerView = binding.recyclerView
        swipe=binding.refresh
        noDataLayout = binding.noAttendance
        calendarDialog = CalendarDialog()
        viewModel.fetchAttendance(viewModel.selectedDate.value!!)
        viewModel.adapter= AttendanceLogAdapter(viewModel.results.value)
        recyclerView.adapter=viewModel.adapter
        recyclerView.adapter?.notifyDataSetChanged()
        viewModel.results.observe(viewLifecycleOwner, Observer {
            viewModel.adapter?.notifyDataSetChanged()
            recyclerView.adapter?.notifyDataSetChanged()
        })

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeDate()
        showCalendarDialog()
        swipe.setOnRefreshListener {
            observeSwipe()
        }
    }

    private fun observeDate() {
        viewModel.selectedDate.observe(viewLifecycleOwner,Observer{
            viewModel.fetchAttendance(it)
            checkForData()
        })
    }

    private fun observeSwipe() {
        viewModel.fetchAttendance(viewModel.selectedDate.value!!)
        checkForData()
        swipe.animate()
        swipe.isRefreshing=false
    }

    fun checkForData() {
        if (viewModel.adapter!!.itemCount >0) {
            noDataLayout.visibility = View.GONE
        }
        else {
            noDataLayout.visibility = View.VISIBLE
        }
    }


    private fun observeLog() {
        viewModel.fetchAttendance(viewModel.selectedDate.value!!)
        viewModel.results.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            viewModel.adapter?.notifyDataSetChanged()
            recyclerView.adapter?.notifyDataSetChanged()
            checkForData()
        })
    }
    private fun showCalendarDialog() {
        calendarButton.setOnClickListener {
            calendarDialog.show(childFragmentManager, "")
        }
    }
}

