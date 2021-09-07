package com.example.attendance_sheet.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.example.attendance_sheet.R
import com.example.attendance_sheet.ui.attendanceEntry.Attendance
import com.example.attendance_sheet.ui.attendanceEntry.AttendanceInterface
import com.example.attendance_sheet.ui.attendanceLog.AttendanceLog
import com.example.attendance_sheet.viewModels.AttendanceViewModel
import com.google.android.material.tabs.TabLayout
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration
import io.realm.mongodb.Credentials
import io.realm.mongodb.User
import io.realm.mongodb.sync.SyncConfiguration

class MainActivity : AppCompatActivity(),AttendanceInterface {
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.enterTransition = null
        tabLayout = findViewById(R.id.tabControl)
        viewPager = findViewById(R.id.viewPager)
        val attenaceAdapter = attendanceAdapter(supportFragmentManager)
        viewPager.adapter = attenaceAdapter
        tabLayout.setupWithViewPager(viewPager, true)
        tabLayout.getTabAt(0)?.setIcon(android.R.drawable.ic_input_add)
        tabLayout.getTabAt(1)?.setIcon(android.R.drawable.ic_input_get)
        val viewModel = ViewModelProviders.of(this).get(AttendanceViewModel::class.java)
        viewModel.selectedDate.value?.let { viewModel.fetchAttendance(it) }
        
    }

    override fun onError(message: String) {

    }

    override fun onStarted() {

    }

    override fun onFailure(message: String) {

    }

    override fun onSuccess() {

    }
}

class attendanceAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
    /**
     * Return the number of views available.
     */
    override fun getCount(): Int {
        return 2;
    }

    /**
     * This method may be called by the ViewPager to obtain a title string
     * to describe the specified page. This method may return null
     * indicating no title for this page. The default implementation returns
     * null.
     *
     * @param position The position of the title requested
     * @return A title for the requested page
     */
    override fun getPageTitle(position: Int): CharSequence {
        return  when(position)
        {
            0 -> "Attendance Entry"
            else -> "Attendance Log"
        }
    }

    /**
     * Return the Fragment associated with a specified position.
     */
    override fun getItem(position: Int): Fragment {
        return  when(position)
        {
            0 -> Attendance()
            else -> AttendanceLog()

        }
    }

}

