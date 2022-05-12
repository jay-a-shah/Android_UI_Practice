package com.example.helioapp.sign_in_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.helioapp.MainActivity
import com.example.helioapp.R
import com.example.helioapp.databinding.ActivitySignInBinding
import com.example.helioapp.databinding.ItemOnBoardingScreenBinding
import com.example.helioapp.signup_screen.SignUpActivity

class SignInActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_in)
        binding.customToolbar.arrowImageView.setOnClickListener {
            finish()
        }
        binding.apply {
            textViewSignUp.setOnClickListener {
                startActivity(Intent(this@SignInActivity,SignUpActivity::class.java))
            }
            btnFacebook.setOnClickListener {
                Toast.makeText(this@SignInActivity,getString(R.string.toast_facebook_btn),Toast.LENGTH_SHORT).show()
            }
            btnApple.setOnClickListener {
                Toast.makeText(this@SignInActivity,getString(R.string.toast_apple_btn),Toast.LENGTH_SHORT).show()
            }
            btnGoogle.setOnClickListener {
                Toast.makeText(this@SignInActivity,getString(R.string.toast_google_btn),Toast.LENGTH_SHORT).show()
            }
        }

    }
}