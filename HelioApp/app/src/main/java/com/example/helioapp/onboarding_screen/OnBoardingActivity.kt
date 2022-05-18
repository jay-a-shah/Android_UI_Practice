package com.example.helioapp.onboarding_screen

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.example.helioapp.MainActivity
import com.example.helioapp.R
import com.example.helioapp.databinding.ActivityOnBoardingBinding
import com.example.helioapp.sign_in_screen.SignInActivity
import com.google.android.material.tabs.TabLayoutMediator

class OnBoardingActivity : AppCompatActivity() {

    var onBoardingItemList: ArrayList<OnBoardingModel> = arrayListOf()
    lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_on_boarding)
        setAdapter()
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        binding.apply {
            viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    when (position) {
                        0 -> {
                            nextBtn.text = getString(R.string.btn_next)
                            iconRectangle.setImageResource(R.drawable.ic_rectangle)
                            iconCircle.setImageResource(R.drawable.icon_default_dot)
                            iconLastCircle.setImageResource(R.drawable.icon_default_dot)
                        } 1 -> {
                            nextBtn.text = getString(R.string.btn_next)
                            iconRectangle.setImageResource(R.drawable.icon_default_dot)
                            iconCircle.setImageResource(R.drawable.ic_rectangle)
                            iconLastCircle.setImageResource(R.drawable.icon_default_dot)
                        } 2 -> {
                            nextBtn.text = getString(R.string.btn_go_ahead)
                            iconRectangle.setImageResource(R.drawable.icon_default_dot)
                            iconCircle.setImageResource(R.drawable.icon_default_dot)
                            iconLastCircle.setImageResource(R.drawable.ic_rectangle)
                        }
                    }
                }
            })
            skipBtn.setOnClickListener {
                startActivity(Intent(this@OnBoardingActivity, SignInActivity::class.java))
                finish()
            }
            nextBtn.setOnClickListener {
                if (viewPager2.currentItem + 1 < onBoardingItemList.count()) {
                    viewPager2.currentItem = binding.viewPager2.currentItem + 1
                } else {
                    startActivity(Intent(this@OnBoardingActivity, SignInActivity::class.java))
                    finish()
                }
            }
        }
    }

    fun setAdapter() {
        setData()
        binding.viewPager2.adapter = OnBoardingAdapter(onBoardingItemList)
    }

    fun setData() {
        onBoardingItemList.apply {
            add(OnBoardingModel(R.drawable.onboarding_three, getString(R.string.header_one), getString(
                        R.string.subHeader
                    )))
            add(OnBoardingModel(R.drawable.onboarding_two, getString(R.string.header_two), getString(
                        R.string.subHeader
                    )))
            add(OnBoardingModel(R.drawable.onboarding_one, getString(R.string.header_three), getString(
                        R.string.subHeader
                    ))
            )
        }
    }
}