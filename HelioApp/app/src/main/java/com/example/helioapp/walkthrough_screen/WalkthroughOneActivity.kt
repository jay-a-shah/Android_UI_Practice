package com.example.helioapp.walkthrough_screen

import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.Shader
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.helioapp.R
import com.example.helioapp.databinding.ActivityWalkthroughOneBinding
import com.example.helioapp.onboarding_screen.OnBoardingActivity
import com.example.helioapp.utils.THREETHOUSAND
import com.example.helioapp.utils.ZERO

class WalkthroughOneActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityWalkthroughOneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialSetup()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnNext -> {
                startActivity(Intent(this@WalkthroughOneActivity, OnBoardingActivity::class.java))
            }
        }
    }

    private fun initialSetup() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_walkthrough_one)
        supportActionBar?.hide()
        binding.apply {
            onClickHandler = this@WalkthroughOneActivity
            val shader = LinearGradient(ZERO, ZERO, ZERO, textViewHeader.textSize, resources.getColor(R.color.light_green), resources.getColor(R.color.dark_green), Shader.TileMode.CLAMP)
            textViewHeader.paint.shader = shader
        }
    }
}