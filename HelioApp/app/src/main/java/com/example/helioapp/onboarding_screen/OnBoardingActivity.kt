package com.example.helioapp.onboarding_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.helioapp.MainActivity
import com.example.helioapp.R
import com.example.helioapp.databinding.ActivityOnBoardingBinding
import com.example.helioapp.utils.ONE
import com.google.android.material.tabs.TabLayoutMediator

class OnBoardingActivity : AppCompatActivity(), View.OnClickListener {

    var onBoardingItemList: ArrayList<OnBoardingModel> = arrayListOf()
    lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialSetup()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.skipBtn -> {
                startActivity(Intent(this@OnBoardingActivity, MainActivity::class.java))
            }
            R.id.nextBtn -> {
                binding.apply {
                    if (viewPagerTwo.currentItem + ONE < onBoardingItemList.count()) {
                        viewPagerTwo.currentItem = viewPagerTwo.currentItem + ONE
                    } else {
                        Toast.makeText(this@OnBoardingActivity, getString(R.string.toast_last_page), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setAdapter() {
        setData()
        binding.viewPagerTwo.adapter = OnBoardingAdapter(onBoardingItemList)
    }

    private fun setData() {
        onBoardingItemList.apply {
            add(OnBoardingModel(R.drawable.onboarding_three, getString(R.string.header_one), getString(R.string.subHeader)))
            add(OnBoardingModel(R.drawable.onboarding_two, getString(R.string.header_two), getString(R.string.subHeader)))
            add(OnBoardingModel(R.drawable.onboarding_one, getString(R.string.header_three), getString(R.string.subHeader)))
        }
    }

    private fun initialSetup() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_on_boarding)
        setAdapter()
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        binding.apply {
            TabLayoutMediator(tabLayout, viewPagerTwo) { tab, position ->
            }.attach()
            onClickHandler = this@OnBoardingActivity
        }
    }
}
