package com.dev_studio.check_in.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.dev_studio.check_in.R
import com.dev_studio.check_in.ui.attendanceEntry.Attendance
import com.dev_studio.check_in.ui.attendanceLog.AttendanceLog
import com.dev_studio.check_in.viewModels.AttendanceViewModel
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    lateinit var attendanceHandler: CompletionHandler
    lateinit var attendanceLogHandler: CompletionHandler
    lateinit var AttendanceAdapter: AttendanceAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModel = ViewModelProviders.of(this).get(AttendanceViewModel::class.java)
        initializeViews()
        initializeTabLayout()
    }

    private fun initializeViews() {
        tabLayout = findViewById(R.id.tabControl)
        viewPager = findViewById(R.id.viewPager)
    }

    private fun initializeTabLayout() {
        AttendanceAdapter = AttendanceAdapter(supportFragmentManager)
        viewPager.adapter = AttendanceAdapter
        tabLayout.setupWithViewPager(viewPager, true)
        tabLayout.getTabAt(0)?.setIcon(android.R.drawable.ic_input_add)
        tabLayout.getTabAt(1)?.setIcon(android.R.drawable.ic_input_get)
    }
}

class AttendanceAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Attendance Entry"
            else -> "Attendance Log"
        }
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> Attendance()
            else -> AttendanceLog()
        }
    }
}

