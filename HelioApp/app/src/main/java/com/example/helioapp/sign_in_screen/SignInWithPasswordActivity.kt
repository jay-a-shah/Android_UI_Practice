package com.example.helioapp.sign_in_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.helioapp.MainActivity
import com.example.helioapp.R
import com.example.helioapp.databinding.ActivitySignInWithPasswordBinding
import com.example.helioapp.forgot_password.ForgotPasswordSelectionActivity
import com.example.helioapp.signup_screen.SignUpActivity
import com.example.helioapp.signup_screen.SignUpViewModel
import com.example.helioapp.utils.isValidEmail
import com.example.helioapp.utils.isValidPassword
import com.example.helioapp.utils.setUpPasswordToggle

class SignInWithPasswordActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignInWithPasswordBinding
    var isPasswordHidden: Boolean = true
    private lateinit var signInViewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in_with_password)
        signInViewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
        binding.signInViewModel = signInViewModel
        validation()
        setSpannableText()
        setUpPasswordToggle(this, isPasswordHidden, binding.editTextPassword, binding.imageBtnEye, false)
        binding.apply { imageBtnEye.isSelected = true
            customToolbar.arrowImageView.setOnClickListener {
                finish()
            }
            editTextPassword.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    setUpPasswordToggle(this@SignInWithPasswordActivity, isPasswordHidden, editTextPassword, imageBtnEye, hasFocus)
                } else {
                    setUpPasswordToggle(this@SignInWithPasswordActivity, isPasswordHidden, editTextPassword, imageBtnEye, hasFocus)
                }
            }
            imageBtnFacebook.setOnClickListener {
                Toast.makeText(this@SignInWithPasswordActivity, getString(R.string.toast_facebook_btn), Toast.LENGTH_SHORT).show()
            }
            imageButtonApple.setOnClickListener {
                Toast.makeText(this@SignInWithPasswordActivity, getString(R.string.toast_apple_btn), Toast.LENGTH_SHORT).show()
            }
            imageBtnGoogle.setOnClickListener {
                Toast.makeText(this@SignInWithPasswordActivity, getString(R.string.toast_google_btn), Toast.LENGTH_SHORT).show()
            }
            imageBtnEye.setOnClickListener {
                it.isSelected = !it.isSelected
                isPasswordHidden = !isPasswordHidden
                setUpPasswordToggle(this@SignInWithPasswordActivity, isPasswordHidden, binding.editTextPassword, imageBtnEye, editTextPassword.hasFocus())
            }
            btnForgotPassword.setOnClickListener {
                startActivity(Intent(this@SignInWithPasswordActivity, ForgotPasswordSelectionActivity::class.java))
            }
            btnSignUp.setOnClickListener {
                isValidPassword(editTextPassword.text.toString())
                if (editTextPassword.text.toString().length >= 8) {
                    Toast.makeText(this@SignInWithPasswordActivity, getString(R.string.toast_password_valid), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@SignInWithPasswordActivity, getString(R.string.toast_password_invalid), Toast.LENGTH_SHORT).show()
                }
                if (isValidEmail(editTextEmail.text.toString())) {
                    Toast.makeText(this@SignInWithPasswordActivity, getString(R.string.toast_email_valid), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@SignInWithPasswordActivity, getString(R.string.toast_email_not_valid), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validation() {
        signInViewModel.email.observe(this) {
            if (it.isEmpty()) {
                binding.btnSignUp.setBackgroundResource(R.drawable.rounded_disable_button)
            } else {
                binding.btnSignUp.setBackgroundResource(R.drawable.rounded_filled_button)
            }
        }
        signInViewModel.password.observe(this) {
            if (it.isNotEmpty()) {
                binding.btnSignUp.setBackgroundResource(R.drawable.rounded_filled_button)
            }
        }
    }

    private fun setSpannableText() {
        val spannable = SpannableString(binding.textViewAlreadyHaveSignIn.text)
        val clickableSpan2: ClickableSpan = object : ClickableSpan() {
            override fun onClick(p0: View) {
                val signInIntent =
                    Intent(this@SignInWithPasswordActivity, SignUpActivity::class.java)
                startActivity(signInIntent)
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.color = ContextCompat.getColor(applicationContext, R.color.primary_500)
                ds.bgColor = ContextCompat.getColor(applicationContext, R.color.white)
            }
        }
        spannable.setSpan(clickableSpan2, 23, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.textViewAlreadyHaveSignIn.text = spannable
        binding.textViewAlreadyHaveSignIn.movementMethod = LinkMovementMethod.getInstance()
    }
}

