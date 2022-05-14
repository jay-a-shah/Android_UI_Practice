package com.example.helioapp.forgot_password

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.helioapp.R
import com.example.helioapp.databinding.ActivityForgotPasswordOtpBinding

class ForgotPasswordOTPActivity : AppCompatActivity() {
    lateinit var  binding: ActivityForgotPasswordOtpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_forgot_password_otp)
        binding.apply {
            arrowImageView.setOnClickListener {
                finish()
            }
            btnVerify.setOnClickListener {
                startActivity(Intent(this@ForgotPasswordOTPActivity,CreateNewPasswordActivity::class.java))
            }
        }
    }
}