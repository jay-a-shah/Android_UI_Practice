package com.example.helioapp.signin

import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.helioapp.MainActivity
import com.example.helioapp.R
import com.example.helioapp.databinding.ActivitySignInBinding
import com.example.helioapp.utils.THIRTY
import com.example.helioapp.utils.TWENTYTWO
import com.example.helioapp.utils.setSpannableText
import com.example.helioapp.utils.showMessage

class SignInActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialSetup()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.arrowImageView -> finish()
            R.id.btnApple ->  showMessage(this, getString(R.string.toast_apple_btn))
            R.id.btnFacebook -> showMessage(this, getString(R.string.toast_facebook_btn))
            R.id.btnGoogle -> showMessage(this, getString(R.string.toast_google_btn))
            R.id.btnSignInWithPassword -> startActivity(Intent(this,MainActivity::class.java))
        }
    }

    private fun initialSetup() {
        supportActionBar?.hide()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        setSpannable()
        binding.clickHandler = this
    }

    private fun setSpannable() {
        val spannable = setSpannableText(binding.textViewDontHaveSignIn.text.toString(), TWENTYTWO, THIRTY, ContextCompat.getColor(this, R.color.primary_500)) {
            startActivity(Intent(this,MainActivity::class.java))
        }
        binding.textViewDontHaveSignIn.apply {
            text = spannable
            movementMethod = LinkMovementMethod.getInstance()
        }
    }
}