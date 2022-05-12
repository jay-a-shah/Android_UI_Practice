package com.example.helioapp.signup_screen

import android.R.attr.button
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.helioapp.MainActivity
import com.example.helioapp.R
import com.example.helioapp.databinding.ActivitySignUpBinding
import com.example.helioapp.utils.setUpPasswordToggle


class SignUpActivity : AppCompatActivity() {
    var isPasswordHidden: Boolean = true
    var signUpViewModel = SignUpViewModel()
    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up)
        setSpannableText()
        setUpPasswordToggle(this,isPasswordHidden,binding.editTextPassword,binding.imageBtnEye)
        validation()
        binding.apply {
            editTextEmail.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                   setEyelogo()
                }
            }
            imageBtnFacebook.setOnClickListener {
                Toast.makeText(this@SignUpActivity,getString(R.string.toast_facebook_btn), Toast.LENGTH_SHORT).show()
            }
            imageButtonApple.setOnClickListener {
                Toast.makeText(this@SignUpActivity,getString(R.string.toast_apple_btn), Toast.LENGTH_SHORT).show()
            }
            imageBtnGoogle.setOnClickListener {
                Toast.makeText(this@SignUpActivity,getString(R.string.toast_google_btn), Toast.LENGTH_SHORT).show()
            }
            imageBtnEye.setOnClickListener {
                isPasswordHidden != isPasswordHidden
                setUpPasswordToggle(this@SignUpActivity,isPasswordHidden,binding.editTextPassword,imageBtnEye)
            }
        }
    }
    private fun validation(){
        signUpViewModel.email.observe(this){email ->
            if (email.isNotEmpty()){
                binding.btnSignUp.setBackgroundResource(R.drawable.rounded_filled_button)
            }

        }
    }
    private fun setEyelogo(){
        binding.apply {
            if(editTextPassword.isEnabled && editTextPassword.isFocused) {
                imageBtnEye.setImageResource(R.drawable.icon_green_hide)
            } else {
                imageBtnEye.setImageResource(R.drawable.icon_eye_hide)
            }
        }
    }

    private fun setSpannableText() {
        val spannable = SpannableString(binding.textViewAlreadyHaveSignIn.text)
        val clickableSpan2: ClickableSpan = object : ClickableSpan() {
            override fun onClick(p0: View) {
                val signInIntent = Intent(this@SignUpActivity, MainActivity::class.java)
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