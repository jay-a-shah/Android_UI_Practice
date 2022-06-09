package com.example.helioapp.signin

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
import com.example.helioapp.signup.SignUpActivity
import com.example.helioapp.utils.*

class SignInWithPasswordActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivitySignInWithPasswordBinding
    var isPasswordHidden: Boolean = true
    private lateinit var signInViewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialSetup()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.arrowImageView -> finish()
            R.id.imageBtnFacebook -> showMessage(this, getString(R.string.toast_facebook_btn))
            R.id.imageButtonApple -> showMessage(this, getString(R.string.toast_apple_btn))
            R.id.imageBtnGoogle -> showMessage(this, getString(R.string.toast_google_btn))
            R.id.imageBtnEye -> {
                binding.apply {
                    imageBtnEye.isSelected = !imageBtnEye.isSelected
                    isPasswordHidden = !isPasswordHidden
                    setUpPasswordToggle(this@SignInWithPasswordActivity, isPasswordHidden, binding.editTextPassword)
                }
            }
            R.id.btnForgotPassword -> {
                startActivity(Intent(this@SignInWithPasswordActivity, MainActivity::class.java))
            }
            R.id.btnSignUp -> performValidation()
        }
    }

    private fun initialSetup() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in_with_password)
        signInViewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
        binding.viewModel = signInViewModel
        validation()
        setSpannable()
        supportActionBar?.hide()
        setUpPasswordToggle(this, isPasswordHidden, binding.editTextPassword)
        binding.apply {
            clickHandler = this@SignInWithPasswordActivity
            editTextPassword.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    imageBtnEye.drawable.setTint(resources.getColor(R.color.primary_500))
                    setUpPasswordToggle(this@SignInWithPasswordActivity, isPasswordHidden, editTextPassword)
                } else {
                    imageBtnEye.drawable.setTint(resources.getColor(R.color.grayscale_500))
                    setUpPasswordToggle(this@SignInWithPasswordActivity, isPasswordHidden, editTextPassword)
                }
            }

        }
    }

    private fun validation() {
        binding.apply {
            signInViewModel.email.observe(this@SignInWithPasswordActivity) {
                if (it.isEmpty()) {
                    btnSignUp.setBackgroundResource(R.drawable.rounded_disable_button)
                } else {
                    btnSignUp.setBackgroundResource(R.drawable.rounded_filled_button)
                }
            }
            signInViewModel.password.observe(this@SignInWithPasswordActivity) {
                if (it.isNotEmpty()) {
                    btnSignUp.setBackgroundResource(R.drawable.rounded_filled_button)
                }
            }
        }
    }

    private fun performValidation() {
        binding.apply {
            isValidPassword(editTextPassword.text.toString())
            if (editTextPassword.text.toString().length >= EIGHT) {
                showMessage(this@SignInWithPasswordActivity, getString(R.string.toast_password_valid))
            } else {
                showMessage(this@SignInWithPasswordActivity, getString(R.string.toast_password_invalid))
            }
            if (isValidEmail(editTextEmail.text.toString())) {
                showMessage(this@SignInWithPasswordActivity, getString(R.string.toast_email_valid))
            } else {
                showMessage(this@SignInWithPasswordActivity, getString(R.string.toast_email_not_valid))
            }
        }
    }

    private fun setSpannable() {
        val spannable = setSpannableText(binding.textViewAlreadyHaveSignIn.text.toString(), TWENTYTHREE, THIRTY, ContextCompat.getColor(this, R.color.primary_500)) {
            startActivity(Intent(this, SignUpActivity::class.java))}
        binding.textViewAlreadyHaveSignIn.apply {
            text = spannable
            movementMethod = LinkMovementMethod.getInstance()
        }
    }
}

