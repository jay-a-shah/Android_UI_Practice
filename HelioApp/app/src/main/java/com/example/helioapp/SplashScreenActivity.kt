package com.example.helioapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.example.helioapp.databinding.ActivitySplashScreenBinding
import com.example.helioapp.home_screen.HomeScreenActivity
import com.example.helioapp.sign_in_screen.SignInActivity
import com.example.helioapp.utils.Constant
import com.example.helioapp.utils.Constant.MAINSCREENKEY
import com.example.helioapp.utils.Constant.ONBOARDKEY
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
        val prefs = getSharedPreferences("SplashScreen", Context.MODE_PRIVATE)
        val intent = if (prefs.getBoolean(ONBOARDKEY,true)) {
            Intent(this,WalkthroughOneActivity::class.java)
        } else {
            if (prefs.getBoolean(MAINSCREENKEY,false)) {
                Intent(this,HomeScreenActivity::class.java)
            } else {
                Intent(this,SignInActivity::class.java)
            }
        }
        handler  = Handler(Looper.getMainLooper())
        handler.postDelayed({
            startActivity(intent)
            finish()
        }, Constant.THREETHOUSAND.toLong())
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacksAndMessages(null)
//        startActivity(Intent(this,WalkthroughOneActivity::class.java))
    }

}