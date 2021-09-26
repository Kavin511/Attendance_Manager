package com.devstudio.check_in.ui.authentication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.devstudio.check_in.R
import com.devstudio.check_in.databinding.ActivityAuthenticationBinding
import com.devstudio.check_in.viewModels.AuthenticationViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration
import io.realm.mongodb.Credentials
import io.realm.mongodb.User
import io.realm.mongodb.sync.SyncConfiguration


class AuthenticationActivity : AppCompatActivity() {
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager2
    lateinit var binding: ActivityAuthenticationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_authentication);
        val viewModel = ViewModelProviders.of(this).get(AuthenticationViewModel::class.java)
        binding.authenticationViewModel = viewModel
        tabLayout = binding.tabLayout
        viewPager = binding.viewPager
        initialiseDb()
        initialiseTabs()
        binding.lifecycleOwner = this
    }

    private fun initialiseDb() {
        Realm.init(this)
        val config = RealmConfiguration.Builder().name("employee").schemaVersion(1).deleteRealmIfMigrationNeeded().allowWritesOnUiThread(false).build()
        Realm.getInstance(config)
        Realm.setDefaultConfiguration(config)
        val appID : String = "attendance-irxim"
        val app = App(
            AppConfiguration.Builder(appID)
            .build())
        val partitionValue: String = "myPartition"
        val credentials: Credentials = Credentials.anonymous()
        var user: User? = null
        app.loginAsync(credentials) {
            if (it.isSuccess) {
                user = app.currentUser()
                val syncConfig = SyncConfiguration.Builder(user!!, partitionValue)
                    .build()
                var realm: Realm
                Realm.getInstanceAsync(syncConfig, object : Realm.Callback() {
                    override fun onSuccess(_realm: Realm) {
                        realm = _realm
                    }
                })
            } else {
                Log.e("TAG", it.error.toString())
            }
        }

    }

    private fun initialiseTabs() {
        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter
        viewPager.setPageTransformer(ZoomOutPageTransformer())
        val auth: MutableList<String> = ArrayList()
        auth.add("Register")
        auth.add("Login")

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = auth[position]
        }.attach()

    }
}

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return SignUpFragment()
            1 -> return SignInFragment()
        }
        return SignUpFragment()
    }
}


class ZoomOutPageTransformer : ViewPager2.PageTransformer {
    override fun transformPage(view: View, position: Float) {
        val pageWidth = view.width
        val pageHeight = view.height
        val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
        val vertMargin = pageHeight * (1 - scaleFactor) / 2
        val horzMargin = pageWidth * (1 - scaleFactor) / 2
        if (position < 0) {
            view.translationX = horzMargin - vertMargin / 2
        } else {
            view.translationX = -horzMargin + vertMargin / 2
        }

        view.scaleX = scaleFactor
        view.scaleY = scaleFactor

        view.alpha = MIN_ALPHA +
                (scaleFactor - MIN_SCALE) /
                (1 - MIN_SCALE) * (1 - MIN_ALPHA)
    }

    companion object {
        private const val MIN_SCALE = 0.85f
        private const val MIN_ALPHA = 0.5f
    }
}