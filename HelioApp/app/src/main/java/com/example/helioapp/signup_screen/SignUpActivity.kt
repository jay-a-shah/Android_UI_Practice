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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.helioapp.R
import com.example.helioapp.databinding.ActivitySignUpBinding
import com.example.helioapp.sign_in_screen.SignInWithPasswordActivity
import com.example.helioapp.utils.setUpPasswordToggle
import com.example.helioapp.utils.showMessage


class SignUpActivity : AppCompatActivity() {

    var isPasswordHidden: Boolean = true
    lateinit var binding: ActivitySignUpBinding
    private lateinit var signUpViewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up)
        signUpViewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        performValidation()
        setSpannableText()
        setUpPasswordToggle(this,isPasswordHidden,binding.editTextPassword,binding.imageBtnEye,false)
        binding.apply {
            viewModel = signUpViewModel
            imageBtnEye.isSelected = true
            customToolbar.arrowImageView.setOnClickListener {
                finish()
            }
            editTextPassword.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
                if (hasFocus){
                        setUpPasswordToggle(this@SignUpActivity,isPasswordHidden,editTextPassword,imageBtnEye,hasFocus)
                    } else {
                        setUpPasswordToggle(this@SignUpActivity,isPasswordHidden,editTextPassword,imageBtnEye,hasFocus)
                    }
                }
            imageBtnFacebook.setOnClickListener {
                showMessage(this@SignUpActivity,getString(R.string.toast_facebook_btn))
            }
            imageButtonApple.setOnClickListener {
                showMessage(this@SignUpActivity,getString(R.string.toast_apple_btn))
            }
            imageBtnGoogle.setOnClickListener {
               showMessage(this@SignUpActivity,getString(R.string.toast_google_btn))
            }
            imageBtnEye.setOnClickListener {
                it.isSelected = !it.isSelected
                isPasswordHidden = !isPasswordHidden
                setUpPasswordToggle(this@SignUpActivity,isPasswordHidden,binding.editTextPassword,imageBtnEye,editTextPassword.hasFocus())
            }
            btnSignUp.setOnClickListener {
               signUpViewModel?.performValidation()
                }
            signUpViewModel?.getSignUpResult()?.observe(this@SignUpActivity) { result ->
                showMessage(this@SignUpActivity,result)
            }
            }
        }

    private fun performValidation(){
        signUpViewModel.email.observe(this) {
            if (it.isEmpty()) {
                binding.btnSignUp.setBackgroundResource(R.drawable.rounded_disable_button)
            } else {
                binding.btnSignUp.setBackgroundResource(R.drawable.rounded_filled_button)
            }
        }
        signUpViewModel.password.observe(this){
            if (it.isNotEmpty()) {
                binding.btnSignUp.setBackgroundResource(R.drawable.rounded_filled_button)
            }
        }
    }

    private fun setSpannableText() {
        val spannable = SpannableString(binding.textViewAlreadyHaveSignIn.text)
        val clickableSpan2: ClickableSpan = object : ClickableSpan() {
            override fun onClick(p0: View) {
                val signInIntent = Intent(this@SignUpActivity, SignInWithPasswordActivity::class.java)
                startActivity(signInIntent)
            }
            override fun updateDrawState(ds: TextPaint) {
                ds.color = ContextCompat.getColor(applicationContext, R.color.primary_500)
                ds.bgColor = ContextCompat.getColor(applicationContext, R.color.white)
            }
        }
        spannable.setSpan(clickableSpan2, 24, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.textViewAlreadyHaveSignIn.text = spannable
        binding.textViewAlreadyHaveSignIn.movementMethod = LinkMovementMethod.getInstance()
    }
}