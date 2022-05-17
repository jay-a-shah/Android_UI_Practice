package com.example.helioapp.home_screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.helioapp.R
import com.example.helioapp.databinding.ActivityHomeScreenBinding

class HomeScreenActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeScreenBinding
    private var homeFragment = HomeFragment()
    private var profileFragment = ProfileFragment()
    private var searchFragment = SearchFragment()
    private var bookingFragment = BookingFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home_screen)
        binding.apply {
        }
        setBottomNavigation()
    }
    fun setBottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.home -> swapFragments(homeFragment)
                R.id.profile -> swapFragments(profileFragment)
                R.id.search -> swapFragments(searchFragment)
                R.id.booking -> swapFragments(bookingFragment)
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun swapFragments(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit()
    }
}