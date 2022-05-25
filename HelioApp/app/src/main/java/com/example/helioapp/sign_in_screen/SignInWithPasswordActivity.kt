package com.example.helioapp.sign_in_screen

import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.helioapp.BaseActivity
import com.example.helioapp.R
import com.example.helioapp.databinding.ActivitySignInWithPasswordBinding
import com.example.helioapp.forgot_password.ForgotPasswordSelectionActivity
import com.example.helioapp.home_screen.HomeScreenActivity
import com.example.helioapp.utils.*
import com.example.helioapp.utils.Constant.THIRTY
import com.example.helioapp.utils.Constant.TWENTYTHREE

class SignInWithPasswordActivity : BaseActivity(), View.OnClickListener {

    lateinit var binding: ActivitySignInWithPasswordBinding
    private lateinit var signInViewModel : SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialSetup()
    }

    override fun onClick(v: View?) {
            when (v?.id) {
                R.id.arrowImageView -> {
                    finish()
                }
                R.id.imageBtnFacebook -> {
                    showMessage(this@SignInWithPasswordActivity, getString(R.string.toast_facebook_btn))
                }
                R.id.imageButtonApple -> {
                    showMessage(this@SignInWithPasswordActivity, getString(R.string.toast_apple_btn))
                }
                R.id.imageBtnGoogle -> {
                    showMessage(this@SignInWithPasswordActivity, getString(R.string.toast_google_btn))
                }
                R.id.imageBtnEye -> {
                    binding.apply {
                        imageBtnEye.isSelected = !imageBtnEye.isSelected
                        setUpPasswordToggle(this@SignInWithPasswordActivity, imageBtnEye.isSelected, binding.editTextPassword, imageBtnEye, editTextPassword.hasFocus())
                    }
                }
                R.id.btnForgotPassword -> {
                    startActivity(Intent(this@SignInWithPasswordActivity, ForgotPasswordSelectionActivity::class.java))
                }
                R.id.btnSignUp -> {

                    signInViewModel.performValidation()
                }
            }
    }

    private fun initialSetup() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in_with_password)
        signInViewModel = ViewModelProvider(this)[SignInViewModel::class.java]
        validation()
        setSpannable()
        setUpPasswordToggle(this, binding.imageBtnEye.isSelected, binding.editTextPassword, binding.imageBtnEye, false)
        binding.apply {
            viewModel = signInViewModel
            clickHandler = this@SignInWithPasswordActivity
            imageBtnEye.isSelected = true
            editTextPassword.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    setUpPasswordToggle(this@SignInWithPasswordActivity, imageBtnEye.isSelected, editTextPassword, imageBtnEye, hasFocus)
                } else {
                    setUpPasswordToggle(this@SignInWithPasswordActivity, imageBtnEye.isSelected, editTextPassword, imageBtnEye, hasFocus)
                }
            }
        }
        signInViewModel.apply {
            logInResult.observe(this@SignInWithPasswordActivity) { apiResult ->
                hideProgressBar()
                if (apiResult.isSuccess){
                    startActivity(Intent(this@SignInWithPasswordActivity,HomeScreenActivity::class.java))
                    finish()
                } else {
                    showMessage(this@SignInWithPasswordActivity,getString(R.string.login_not_successfull))
                }
            }
            validateData.observe(this@SignInWithPasswordActivity) { validation ->
                if (validation == R.string.valid_credentials) {
                    showProgressBar()
                } else {
                    showMessage(this@SignInWithPasswordActivity,getString(validation))
                }
            }
        }
    }

    private fun validation() {
        binding.apply {
            signInViewModel.email.observe(this@SignInWithPasswordActivity) {
               setBtnBackground()
            }
            signInViewModel.password.observe(this@SignInWithPasswordActivity) {
               setBtnBackground()
            }
        }
    }

    private fun setSpannable() {
        val spannable = setSpannableText(binding.textViewAlreadyHaveSignIn.text.toString(), TWENTYTHREE, THIRTY, ContextCompat.getColor(this, R.color.primary_500)) {}
        binding.textViewAlreadyHaveSignIn.apply {
            text = spannable
            movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private fun setBtnBackground() {
        binding.apply {
            btnSignUp.isSelected = editTextPassword.text.toString().isNotEmpty() && editTextEmail.text.toString().isNotEmpty()
        }
    }
}

