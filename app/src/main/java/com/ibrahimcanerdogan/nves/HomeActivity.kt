package com.ibrahimcanerdogan.nves

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.card.MaterialCardView
import com.google.android.material.navigation.NavigationView
import com.ibrahimcanerdogan.nves.data.model.News
import com.ibrahimcanerdogan.nves.databinding.ActivityHomeBinding
import com.ibrahimcanerdogan.nves.util.NewsCategory
import com.ibrahimcanerdogan.nves.util.Resource
import com.ibrahimcanerdogan.nves.view.adapter.NewsAdapter
import com.ibrahimcanerdogan.nves.view.viewmodel.NewsViewModel
import com.ibrahimcanerdogan.nves.view.viewmodel.NewsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)

        binding.apply {
            navigationDrawer.setNavigationItemSelectedListener(this@HomeActivity)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()
            buttonDrawerMenu.setOnClickListener { drawerLayout.open() }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.itemLocation -> {
                Toast.makeText(this, "Coming Soon...", Toast.LENGTH_SHORT).show()
            }
            R.id.itemReview -> {
                Toast.makeText(this, "Coming Soon...", Toast.LENGTH_SHORT).show()
            }
            R.id.itemShare -> {
                Toast.makeText(this, "Coming Soon...", Toast.LENGTH_SHORT).show()
            }
        }
        binding.drawerLayout.close()
        return true
    }

    override fun onStart() {
        super.onStart()
        fullScreenMode()
    }
    override fun onResume() {
        super.onResume()
        fullScreenMode()
    }

    private fun fullScreenMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            if (window.insetsController != null) {
                window.insetsController!!.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                window.insetsController!!.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

    companion object {
        private val TAG = HomeActivity::class.simpleName.toString()
    }
}