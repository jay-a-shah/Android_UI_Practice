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
import androidx.core.app.RemoteInput
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.helioapp.R
import com.example.helioapp.databinding.ActivitySignInBinding
import com.example.helioapp.signup_screen.SignUpActivity
import com.example.helioapp.utils.Constant
import com.example.helioapp.utils.showMessage

class SignInActivity : AppCompatActivity(),View.OnClickListener {

    lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        setSpannableText()
        binding.apply {
            clickHandler = this@SignInActivity
        }
    }

    override fun onClick(v: View?) {
       when (v?.id) {
           R.id.arrowImageView -> {
               finish()
           }
           R.id.btnSignInWithPassword -> {
               startActivity(Intent(this@SignInActivity, SignInWithPasswordActivity::class.java))
               finish()
           }
           R.id.btnFacebook -> {
              showMessage(this,getString(R.string.toast_facebook_btn))
           }
           R.id.btnApple -> {
              showMessage(this,getString(R.string.toast_apple_btn))
           }
           R.id.btnGoogle -> {
               showMessage(this,getString(R.string.toast_google_btn))
           }
       }
    }

    private fun setSpannableText() {
        val spannable = SpannableString(binding.textViewDontHaveSignIn.text)
        val clickableSpan2: ClickableSpan = object : ClickableSpan() {
            override fun onClick(p0: View) {
                val signInIntent = Intent(this@SignInActivity, SignUpActivity::class.java)
                startActivity(signInIntent)
            }

            override fun updateDrawState(drawState: TextPaint) {
                drawState.color = ContextCompat.getColor(applicationContext, R.color.primary_500)
                drawState.bgColor = ContextCompat.getColor(applicationContext, R.color.white)
            }
        }
        spannable.setSpan(clickableSpan2, 23, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.textViewDontHaveSignIn.text = spannable
        binding.textViewDontHaveSignIn.movementMethod = LinkMovementMethod.getInstance()
    }
}