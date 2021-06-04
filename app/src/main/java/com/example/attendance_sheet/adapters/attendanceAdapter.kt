package com.example.attendance_sheet.adapters

import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.attendance_sheet.ui.attendanceEntry.attendanceEntry
import com.example.attendance_sheet.ui.attendanceLog.attendanceLog

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
    override fun getPageTitle(position: Int): CharSequence? {
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
            0 -> attendanceEntry()
            else -> attendanceLog()

        }
    }

}