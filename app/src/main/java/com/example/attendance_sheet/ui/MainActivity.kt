package com.example.attendance_sheet.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.attendance_sheet.R
import com.example.attendance_sheet.adapters.attendanceAdapter
import com.google.android.material.tabs.TabLayout
import io.realm.Realm
import io.realm.RealmConfiguration

class MainActivity : AppCompatActivity() {
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
        Realm.init(baseContext)
        val config = RealmConfiguration.Builder().name("attendance.db").schemaVersion(1).allowWritesOnUiThread(true)
            .deleteRealmIfMigrationNeeded().build();

        Realm.getInstance(config)
    }
}


