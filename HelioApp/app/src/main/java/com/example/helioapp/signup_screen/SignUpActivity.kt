package com.example.helioapp.signup_screen

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.View.OnFocusChangeListener
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.helioapp.BaseActivity
import com.example.helioapp.R
import com.example.helioapp.databinding.ActivitySignUpBinding
import com.example.helioapp.sign_in_screen.SignInWithPasswordActivity
import com.example.helioapp.utils.setUpPasswordToggle
import com.example.helioapp.utils.showMessage

class SignUpActivity : BaseActivity() {

    lateinit var binding: ActivitySignUpBinding
    private lateinit var signUpViewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        signUpViewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        performValidation()
        setSpannableText()
        setUpPasswordToggle(this, binding.imageBtnEye.isSelected, binding.editTextPassword, binding.imageBtnEye, false)
        binding.apply {
            viewModel = signUpViewModel
            imageBtnEye.isSelected = true
            customToolbar.arrowImageView.setOnClickListener {
                finish()
            }
            editTextPassword.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    setUpPasswordToggle(this@SignUpActivity, imageBtnEye.isSelected, editTextPassword, imageBtnEye, hasFocus)
                } else {
                    setUpPasswordToggle(this@SignUpActivity, imageBtnEye.isSelected, editTextPassword, imageBtnEye, hasFocus)
                }
            }
            imageBtnFacebook.setOnClickListener {
                showMessage(this@SignUpActivity, getString(R.string.toast_facebook_btn))
            }
            imageButtonApple.setOnClickListener {
                showMessage(this@SignUpActivity, getString(R.string.toast_apple_btn))
            }
            imageBtnGoogle.setOnClickListener {
                showMessage(this@SignUpActivity, getString(R.string.toast_google_btn))
            }
            imageBtnEye.setOnClickListener {
                it.isSelected = !it.isSelected
                //isPasswordHidden = !isPasswordHidden
                setUpPasswordToggle(this@SignUpActivity, it.isSelected, binding.editTextPassword, imageBtnEye, editTextPassword.hasFocus())
            }
            btnSignUp.setOnClickListener {
                signUpViewModel.performValidation()
                signUpViewModel.progressBarStatus.observe(this@SignUpActivity) { status ->
                    if(status) { showProgressBar() } else { hideProgressBar() }
                }
            }
            signUpViewModel.getSignUpResult().observe(this@SignUpActivity) { result ->
                showMessage(this@SignUpActivity, result)
            }
        }
    }

    private fun performValidation() {
        signUpViewModel.email.observe(this) {
            if (it.isEmpty()) {
                binding.btnSignUp.alpha = 0.5f
            } else {
                binding.btnSignUp.alpha = 0.1f
            }
        }
        signUpViewModel.password.observe(this) {
            if (it.isNotEmpty()) {
                binding.btnSignUp.setBackgroundResource(R.drawable.rounded_filled_button)
            }
        }
    }

    private fun setSpannableText() {
        val spannable = SpannableString(binding.textViewAlreadyHaveSignIn.text)
        val clickableSpan2: ClickableSpan = object : ClickableSpan() {

            override fun onClick(p0: View) {
                startActivity(Intent(this@SignUpActivity,SignInWithPasswordActivity::class.java))
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.apply {
                    color = ContextCompat.getColor(applicationContext, R.color.primary_500)
                    bgColor = ContextCompat.getColor(applicationContext, R.color.white)
                }
            }
        }
        spannable.setSpan(clickableSpan2, 24, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.apply {
            textViewAlreadyHaveSignIn.text = spannable
            textViewAlreadyHaveSignIn.movementMethod = LinkMovementMethod.getInstance()
        }
    }
}