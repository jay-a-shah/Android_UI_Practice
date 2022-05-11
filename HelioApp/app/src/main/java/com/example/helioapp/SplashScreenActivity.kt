package com.example.helioapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.example.helioapp.databinding.ActivitySplashScreenBinding
import com.example.helioapp.utils.THREETHOUSAND
import com.example.helioapp.utils.TWOTHOUSAND
import com.example.helioapp.walkthrough_screen.WalkthroughOneActivity

class SplashScreenActivity : AppCompatActivity() {

    private  lateinit var topAnimation: Animation
    lateinit var binding: ActivitySplashScreenBinding
    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash_screen)
        supportActionBar?.hide()
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_up_to_bottom_logo)
        binding.splashLogo.animation = topAnimation
        handler  = Handler(Looper.getMainLooper())
        handler.postDelayed({
            startActivity(Intent(this,WalkthroughOneActivity::class.java))
            finish()
        }, THREETHOUSAND.toLong())
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacksAndMessages(null)
//        startActivity(Intent(this,WalkthroughOneActivity::class.java))
    }
}