package com.example.helioapp.forgot_password

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.helioapp.R
import com.example.helioapp.databinding.ActivityForgotPasswordOtpBinding
import com.example.helioapp.signup_screen.SignUpActivity
import com.example.helioapp.signup_screen.SignUpViewModel

class ForgotPasswordOTPActivity : AppCompatActivity() {
    lateinit var  binding: ActivityForgotPasswordOtpBinding
    private lateinit var forgotPasswordViewModel: ForgotPasswordOTPModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_forgot_password_otp)
        countDownTimer()
        setSpannableText()
        binding.apply {
            arrowImageView.setOnClickListener {
                finish()
            }
            btnVerify.setOnClickListener {
                startActivity(Intent(this@ForgotPasswordOTPActivity,CreateNewPasswordActivity::class.java))
            }
        }
    }
    private fun countDownTimer() { object : CountDownTimer(60000, 1000) {
            // Callback function, fired on regular interval
            override fun onTick(millisUntilFinished: Long) {
                val s = (millisUntilFinished/1000)
                binding.textViewTimer.text = getString(R.string.tv_resend_code_in_53_s,s.toString())
            }

        override fun onFinish() {
            binding.textViewTimer.text = getString(R.string.tv_send_code_again)
        }
    }.start()
    }
    private fun setSpannableText() {
        val spannable = SpannableString(binding.textViewTimer.text)
        val clickableSpan2: ClickableSpan = object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                ds.color = ContextCompat.getColor(applicationContext, R.color.primary_500)
                ds.bgColor = ContextCompat.getColor(applicationContext, R.color.white)
            }

            override fun onClick(p0: View) {
                TODO("Not yet implemented")
            }
        }
        spannable.setSpan(clickableSpan2, 2, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.textViewTimer.text = spannable
    }
}