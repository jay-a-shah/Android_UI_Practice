package com.example.helioapp.walkthrough_screen

import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.Shader
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.databinding.DataBindingUtil
import com.example.helioapp.R
import com.example.helioapp.databinding.ActivityWalkthroughOneBinding
import com.example.helioapp.onboarding_screen.OnBoardingActivity

class WalkthroughOneActivity : AppCompatActivity() {

    lateinit var binding: ActivityWalkthroughOneBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_walkthrough_one)
        supportActionBar?.hide()
        val shader = LinearGradient(0f, 0f, 0f, binding.textViewHeader.textSize, resources.getColor(R.color.light_green) , resources.getColor(R.color.dark_green), Shader.TileMode.CLAMP)
        binding.textViewHeader.paint.shader = shader
        binding.btnNext.setOnClickListener {
            startActivity(Intent(this,OnBoardingActivity::class.java))
            finish()
        }
    }
}