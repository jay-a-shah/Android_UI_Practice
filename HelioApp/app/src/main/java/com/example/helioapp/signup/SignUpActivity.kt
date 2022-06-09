package com.example.helioapp.signup

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.helioapp.R
import com.example.helioapp.databinding.ActivitySignUpBinding
import com.example.helioapp.utils.*

class SignUpActivity : AppCompatActivity(), View.OnClickListener {

    var isPasswordHidden: Boolean = true
    lateinit var binding: ActivitySignUpBinding
    private lateinit var signUpViewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialSetup()
    }

    private fun validation() {
        binding.apply {
            signUpViewModel.email.observe(this@SignUpActivity) {
                if (it.isEmpty()) {
                    btnSignUp.setBackgroundResource(R.drawable.rounded_disable_button)
                } else {
                    btnSignUp.setBackgroundResource(R.drawable.rounded_filled_button)
                }
            }
            signUpViewModel.password.observe(this@SignUpActivity) {
                if (it.isNotEmpty()) {
                    btnSignUp.setBackgroundResource(R.drawable.rounded_filled_button)
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.arrowImageView -> finish()
            R.id.btnSignUp -> {
                binding.apply {
                    isValidPassword(editTextPassword.text.toString())
                    if (editTextPassword.text.toString().length >= EIGHT) {
                        showMessage(this@SignUpActivity, getString(R.string.toast_password_valid))
                    } else {
                        showMessage(this@SignUpActivity, getString(R.string.toast_password_invalid))
                    }
                    if (isValidEmail(editTextEmail.text.toString())) {
                        showMessage(this@SignUpActivity, getString(R.string.toast_email_valid))
                    } else {
                        showMessage(this@SignUpActivity, getString(R.string.toast_email_not_valid))
                    }
                }
            }
            R.id.imageBtnFacebook -> showMessage(this, getString(R.string.toast_facebook_btn))
            R.id.imageButtonApple -> showMessage(this, getString(R.string.toast_apple_btn))
            R.id.imageBtnGoogle -> showMessage(this, getString(R.string.toast_google_btn))
            R.id.imageBtnEye -> {
                binding.apply {
                    imageBtnEye.isSelected = !imageBtnEye.isSelected
                    isPasswordHidden = !isPasswordHidden
                    setUpPasswordToggle(this@SignUpActivity, isPasswordHidden, binding.editTextPassword)
                }
            }
        }
    }

    private fun initialSetup() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        signUpViewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        binding.viewModel = signUpViewModel
        validation()
        setSpannable()
        supportActionBar?.hide()
        setUpPasswordToggle(this, isPasswordHidden, binding.editTextPassword)
        binding.apply {
            clickHandler = this@SignUpActivity
            editTextPassword.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    imageBtnEye.drawable.setTint(resources.getColor(R.color.primary_500))
                    setUpPasswordToggle(this@SignUpActivity, isPasswordHidden, editTextPassword)
                } else {
                    imageBtnEye.drawable.setTint(resources.getColor(R.color.primary_500))
                    setUpPasswordToggle(this@SignUpActivity, isPasswordHidden, editTextPassword)
                }
            }
        }
    }

    private fun setSpannable() {
        val spannable = setSpannableText(binding.textViewAlreadyHaveSignIn.text.toString(), TWENTYFOUR, THIRTYTWO, ContextCompat.getColor(this, R.color.primary_500)) {}
        binding.textViewAlreadyHaveSignIn.apply {
            text = spannable
            movementMethod = LinkMovementMethod.getInstance()
        }
    }

}