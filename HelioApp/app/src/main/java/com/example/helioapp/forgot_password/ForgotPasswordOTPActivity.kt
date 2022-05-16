package com.example.helioapp.forgot_password

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.helioapp.R
import com.example.helioapp.databinding.ActivityForgotPasswordOtpBinding
import com.example.helioapp.signup_screen.SignUpActivity
import com.example.helioapp.signup_screen.SignUpViewModel

class ForgotPasswordOTPActivity : AppCompatActivity() {
    lateinit var binding: ActivityForgotPasswordOtpBinding
    private lateinit var forgotPasswordViewModel: ForgotPasswordOTPModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password_otp)
        forgotPasswordViewModel = ViewModelProvider(this)[ForgotPasswordOTPModel::class.java]
        countDownTimer()
        binding.viewModel = forgotPasswordViewModel
        binding.apply {
            arrowImageView.setOnClickListener {
                finish()
            }
            btnVerify.setOnClickListener {
                startActivity(
                    Intent(
                        this@ForgotPasswordOTPActivity,
                        CreateNewPasswordActivity::class.java
                    )
                )
            }
            forgotPasswordViewModel.etOne.observe(this@ForgotPasswordOTPActivity) {
                if (it.length == 1) {
                    editTextTwo.requestFocus()
                }
                if (it.isNotEmpty()) {
                    editTextOne.clearFocus()
                }
            }
            forgotPasswordViewModel.etTwo.observe(this@ForgotPasswordOTPActivity) {
                if (it.length == 1) {
                    editTextThree.requestFocus()
                }
                if (it.isEmpty()) {
                    editTextOne.requestFocus()
                }
            }
            forgotPasswordViewModel.etThree.observe(this@ForgotPasswordOTPActivity) {
                if (it.length == 1) {
                    editTextFour.requestFocus()
                }
                if (it.isEmpty()) {
                    editTextTwo.requestFocus()
                }
            }
            forgotPasswordViewModel.etFour.observe(this@ForgotPasswordOTPActivity) {
                if (it.length == 1) {
                    editTextFour.clearFocus()
                    val hidekeyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    hidekeyboard?.hideSoftInputFromWindow(editTextFour.windowToken, 0)
                }
                if (it.isEmpty()) {
                    editTextThree.requestFocus()
                }
            }
        }
    }

    private fun countDownTimer() {
        object : CountDownTimer(60000, 1000) {
            // Callback function, fired on regular interval
            override fun onTick(millisUntilFinished: Long) {
                val s = (millisUntilFinished / 1000)
                setSpannableText(s.toString())
            }
            override fun onFinish() {
                binding.textViewTimer.text = getString(R.string.tv_send_code_again)
            }
        }.start()
    }

    private fun setSpannableText(s: String) {
        val spannable = SpannableStringBuilder(getString(R.string.tv_resend_code_in_53_s, s))
        spannable.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.primary_500)), 15, 17,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.textViewTimer.text = spannable
    }
}