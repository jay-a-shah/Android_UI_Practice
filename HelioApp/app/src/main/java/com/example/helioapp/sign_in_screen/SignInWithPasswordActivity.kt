package com.example.helioapp.sign_in_screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.helioapp.R
import com.example.helioapp.databinding.ActivitySignInWithPasswordBinding

class SignInWithPasswordActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignInWithPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_in_with_password)

    }
}