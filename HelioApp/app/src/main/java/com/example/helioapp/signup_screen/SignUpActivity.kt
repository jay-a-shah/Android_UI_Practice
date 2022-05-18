package com.example.helioapp.signup_screen

import android.R.attr.button
import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.helioapp.MainActivity
import com.example.helioapp.R
import com.example.helioapp.databinding.ActivitySignUpBinding
import com.example.helioapp.onboarding_screen.OnBoardingModel
import com.example.helioapp.sign_in_screen.SignInWithPasswordActivity
import com.example.helioapp.utils.*
import com.example.helioapp.utils.Constant.BASEURL
import com.example.helioapp.utils.Constant.EIGHT
import com.example.helioapp.webservices_without_retrofit.Callbacks
import org.json.JSONObject
import java.net.URL


class SignUpActivity : AppCompatActivity() {
    var isPasswordHidden: Boolean = true

    lateinit var binding: ActivitySignUpBinding
    private lateinit var signUpViewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up)
        signUpViewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        binding.signUpViewModel = signUpViewModel
        validation()
        setSpannableText()
        setUpPasswordToggle(this,isPasswordHidden,binding.editTextPassword,binding.imageBtnEye,false)
        binding.apply {
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
                Toast.makeText(this@SignUpActivity,getString(R.string.toast_facebook_btn), Toast.LENGTH_SHORT).show()
            }
            imageButtonApple.setOnClickListener {
                Toast.makeText(this@SignUpActivity,getString(R.string.toast_apple_btn), Toast.LENGTH_SHORT).show()
            }
            imageBtnGoogle.setOnClickListener {
                Toast.makeText(this@SignUpActivity,getString(R.string.toast_google_btn), Toast.LENGTH_SHORT).show()
            }
            imageBtnEye.setOnClickListener {
                it.isSelected = !it.isSelected
                isPasswordHidden = !isPasswordHidden
                setUpPasswordToggle(this@SignUpActivity,isPasswordHidden,binding.editTextPassword,imageBtnEye,editTextPassword.hasFocus())
            }
            btnSignUp.setOnClickListener {
                isValidPassword(editTextPassword.text.toString())
                if (editTextPassword.text.toString().length >= EIGHT && isValidEmail(editTextEmail.text.toString())) {
                    val credential = JSONObject()
                    credential.apply {
                        put("email", editTextEmail.text.toString())
                        put("password", editTextPassword.text.toString())
                        put("name","Test123")
                    }
                    val url = URL(BASEURL)
                    signUpViewModel?.apiCall(credential, url, "POST",object : Callbacks{
                        override fun onSuccessCallback(output: String) {
                            runOnUiThread {
                                Toast.makeText(this@SignUpActivity,"User Created", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailureCallback(responseCode: Int, output: String) {
                            runOnUiThread {
                                Toast.makeText(this@SignUpActivity,"User Not Created", Toast.LENGTH_SHORT).show()
                            }
                        }

                    },Any::class.java)
                } else {
                    Toast.makeText(this@SignUpActivity,getString(R.string.toast_credential_invalid),Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun validation(){
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