package com.example.helioapp.onboarding_screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.example.helioapp.R
import com.example.helioapp.databinding.ActivityOnBoardingBinding
import com.google.android.material.tabs.TabLayoutMediator

class OnBoardingActivity : AppCompatActivity() {

    var onBoardingItemList: ArrayList<OnBoardingModel> = arrayListOf()
    lateinit var binding: ActivityOnBoardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_on_boarding)
        setAdapter()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
        }.attach()
    }
    fun setAdapter(){
        setData()
        binding.viewPager2.adapter = OnBoardingAdapter(onBoardingItemList)
    }
    fun setData(){
        onBoardingItemList.apply {
            add(OnBoardingModel(R.drawable.background_image,getString(R.string.header_one),getString(
                R.string.subHeader)))
            add(OnBoardingModel(R.drawable.onboarding_two,getString(R.string.header_two),getString(
                R.string.subHeader)))
            add(OnBoardingModel(R.drawable.onboarding_one_hdpi,getString(R.string.header_three),getString(
                R.string.subHeader)))
        }
    }
}