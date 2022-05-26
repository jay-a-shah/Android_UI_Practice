package com.example.helioapp.signup_screen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import android.view.View.OnFocusChangeListener
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.helioapp.BaseActivity
import com.example.helioapp.R
import com.example.helioapp.databinding.ActivitySignUpBinding
import com.example.helioapp.home_screen.HomeScreenActivity
import com.example.helioapp.sign_in_screen.SignInWithPasswordActivity
import com.example.helioapp.utils.Constant
import com.example.helioapp.utils.Constant.THIRTYTWO
import com.example.helioapp.utils.Constant.TWENTYFOUR
import com.example.helioapp.utils.setSpannableText
import com.example.helioapp.utils.setUpPasswordToggle
import com.example.helioapp.utils.showMessage

class SignUpActivity : BaseActivity(), View.OnClickListener {

    lateinit var binding: ActivitySignUpBinding
    private lateinit var signUpViewModel: SignUpViewModel

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
                    showMessage(this@SignUpActivity, getString(R.string.toast_facebook_btn))
                }
                R.id.imageButtonApple -> {
                    showMessage(this@SignUpActivity, getString(R.string.toast_apple_btn))
                }
                R.id.imageBtnGoogle -> {
                    showMessage(this@SignUpActivity, getString(R.string.toast_google_btn))
                }
                R.id.imageBtnEyeSignUp -> {
                    binding.apply {
                        imageBtnEyeSignUp.isSelected = !imageBtnEyeSignUp.isSelected
                        setUpPasswordToggle(this@SignUpActivity, imageBtnEyeSignUp.isSelected, binding.editTextPassword, imageBtnEyeSignUp, editTextPassword.hasFocus())
                    }
                }
                R.id.btnSignUp -> {
                    signUpViewModel.performValidation()
                }
        }
    }

    private fun initialSetup() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        signUpViewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        performValidation()
        setSpannable()
        setUpPasswordToggle(this, binding.imageBtnEyeSignUp.isSelected, binding.editTextPassword, binding.imageBtnEyeSignUp, false)
        binding.apply {
            clickHandler = this@SignUpActivity
            customToolbar.toolbarClickHandler = this@SignUpActivity
            viewModel = signUpViewModel
            imageBtnEyeSignUp.isSelected = true
            editTextPassword.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    setUpPasswordToggle(this@SignUpActivity, imageBtnEyeSignUp.isSelected, editTextPassword, imageBtnEyeSignUp, hasFocus)
                } else {
                    setUpPasswordToggle(this@SignUpActivity, imageBtnEyeSignUp.isSelected, editTextPassword, imageBtnEyeSignUp, hasFocus)
                }
            }
            signUpViewModel.apply {
                signUpResult.observe(this@SignUpActivity) { apiResult ->
                    hideProgressBar()
                    binding.btnSignUp.visibility = View.VISIBLE
                    if (apiResult.isSuccess){
                        val prefs = getSharedPreferences(Constant.SHAREDKEY, Context.MODE_PRIVATE)
                        prefs.edit().putBoolean(Constant.MAINSCREENKEY,true).apply()
                        showMessage(this@SignUpActivity,apiResult.dataClassBody.toString())
                        startActivity(Intent(this@SignUpActivity,HomeScreenActivity::class.java))
                        finish()
                    } else {
                        showMessage(this@SignUpActivity,apiResult.dataClassBody.toString())
                    }
                }
                validateData.observe(this@SignUpActivity) { validation ->
                    if (validation == R.string.valid_credentials) {
                        binding.btnSignUp.visibility = View.GONE
                        showProgressBar()
                    } else {
                        showMessage(this@SignUpActivity,getString(validation))
                    }
                }
            }
        }
    }

    private fun performValidation() {
        binding.apply {
            signUpViewModel.email.observe(this@SignUpActivity) {
                setBtnBackground()
            }
            signUpViewModel.password.observe(this@SignUpActivity) {
               setBtnBackground()
            }
        }
    }

    private fun setSpannable() {
        val spannable = setSpannableText(binding.textViewAlreadyHaveSignIn.text.toString(), TWENTYFOUR, THIRTYTWO, ContextCompat.getColor(this, R.color.primary_500)) {
            startActivity(Intent(this,SignInWithPasswordActivity::class.java))
            finish()
        }
        binding.textViewAlreadyHaveSignIn.apply {
            text = spannable
            movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private fun setBtnBackground() {
        binding.apply {
            btnSignUp.isSelected = editTextPassword.text.toString().isNotEmpty() && editTextEmail.text.toString().isNotEmpty()
            btnSignUp.isEnabled =  editTextPassword.text.toString().isNotEmpty() && editTextEmail.text.toString().isNotEmpty()
        }
    }

}