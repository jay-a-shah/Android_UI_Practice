package com.example.helioapp.signup_screen

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.view.View
import android.view.View.OnFocusChangeListener
import androidx.core.content.ContextCompat
import androidx.core.text.toSpannable
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.helioapp.BaseActivity
import com.example.helioapp.R
import com.example.helioapp.databinding.ActivitySignUpBinding
import com.example.helioapp.home_screen.HomeScreenActivity
import com.example.helioapp.sign_in_screen.SignInWithPasswordActivity
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
                R.id.imageBtnEye -> {
                    binding.apply {
                        imageBtnEye.isSelected = imageBtnEye.isSelected
                        setUpPasswordToggle(this@SignUpActivity, imageBtnEye.isSelected, binding.editTextPassword, imageBtnEye, editTextPassword.hasFocus())
                    }
                }
                R.id.btnSignUp -> {
                    showProgressBar()
                    signUpViewModel.performValidation()
                }
        }
    }

    private fun initialSetup() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        signUpViewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        performValidation()
        setSpannable()
        setUpPasswordToggle(this, binding.imageBtnEye.isSelected, binding.editTextPassword, binding.imageBtnEye, false)
        binding.apply {
            clickHandler = this@SignUpActivity
            customToolbar.toolbarClickHandler = this@SignUpActivity
            viewModel = signUpViewModel
            imageBtnEye.isSelected = true
            editTextPassword.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    setUpPasswordToggle(this@SignUpActivity, imageBtnEye.isSelected, editTextPassword, imageBtnEye, hasFocus)
                } else {
                    setUpPasswordToggle(this@SignUpActivity, imageBtnEye.isSelected, editTextPassword, imageBtnEye, hasFocus)
                }
            }
            signUpViewModel.signUpResult.observe(this@SignUpActivity) { apiResult ->
                hideProgressBar()
                if (apiResult.isSuccess){
                    startActivity(Intent(this@SignUpActivity,HomeScreenActivity::class.java))
                } else {
                    showMessage(this@SignUpActivity,getString(R.string.user_not_created))
                }
            }
            signUpViewModel.validateData.observe(this@SignUpActivity) { validateData ->
                showMessage(this@SignUpActivity,getString(validateData))
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
        val spannable = setSpannableText(binding.textViewAlreadyHaveSignIn.text.toString(), TWENTYFOUR, THIRTYTWO, ContextCompat.getColor(this, R.color.primary_500)) {}
        binding.apply {
            textViewAlreadyHaveSignIn.text = spannable
            textViewAlreadyHaveSignIn.movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private fun setBtnBackground() {
        binding.apply {
            btnSignUp.isSelected = editTextPassword.text.toString().isNotEmpty() && editTextEmail.text.toString().isNotEmpty()
        }
    }

}