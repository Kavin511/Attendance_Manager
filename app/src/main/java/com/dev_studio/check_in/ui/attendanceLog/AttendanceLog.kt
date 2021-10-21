package com.dev_studio.check_in.ui.attendanceLog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dev_studio.check_in.R
import com.dev_studio.check_in.databinding.FragmentAttendanceLogBinding
import com.dev_studio.check_in.viewModels.AttendanceViewModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton


class AttendanceLog : Fragment() {

    lateinit var calendarButton: ExtendedFloatingActionButton
    lateinit var binding: FragmentAttendanceLogBinding
    lateinit var viewModel: AttendanceViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var noDataLayout: LinearLayout
    lateinit var swipe: SwipeRefreshLayout
    lateinit var calendarDialog: CalendarDialog
    private lateinit var itemTouchHelper: ItemTouchHelper
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
        swipe = binding.refresh
        noDataLayout = binding.noAttendance
        calendarDialog = CalendarDialog()
        viewModel.fetchAttendance(viewModel.selectedDate.value!!)
        setUpRecyclerView()
        viewModel.results.observe(viewLifecycleOwner, Observer {
            viewModel.adapter?.notifyDataSetChanged()
            recyclerView.adapter?.notifyDataSetChanged()
//            Toast.makeText(context, "observe", Toast.LENGTH_SHORT).show()
        })

    }

    private fun setUpRecyclerView() {
        viewModel.adapter = AttendanceLogAdapter(viewModel.results.value)
        recyclerView.adapter = viewModel.adapter
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
        viewModel.selectedDate.observe(viewLifecycleOwner, Observer {
            viewModel.fetchAttendance(it)
            checkForData()
        })
    }

    private fun observeSwipe() {
        viewModel.fetchAttendance(viewModel.selectedDate.value!!)
        checkForData()
        swipe.animate()
        swipe.isRefreshing = false
    }

    fun checkForData() {
        viewModel.isHavingRecords = viewModel.adapter!!.itemCount > 0
    }


    private fun observeLog() {
        viewModel.fetchAttendance(viewModel.selectedDate.value!!)
        viewModel.results.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            viewModel.adapter?.notifyDataSetChanged()
            recyclerView.adapter?.notifyDataSetChanged()
//            Toast.makeText(context, "observelog()", Toast.LENGTH_SHORT).show()
            checkForData()
        })
    }

    private fun showCalendarDialog() {
        calendarButton.setOnClickListener {
            calendarDialog.show(childFragmentManager, "")
        }
    }
}

