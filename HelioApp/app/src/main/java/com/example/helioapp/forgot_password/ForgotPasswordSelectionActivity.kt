package com.example.helioapp.forgot_password

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.helioapp.R
import com.example.helioapp.databinding.ActivityForgotPasswordSelectionBinding
import com.example.helioapp.databinding.ActivitySignInWithPasswordBinding

class ForgotPasswordSelectionActivity : AppCompatActivity() {

    lateinit var binding: ActivityForgotPasswordSelectionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_forgot_password_selection)
        binding.apply {
           arrowImageView.setOnClickListener {
                finish()
            }
            cardViewOne.setOnClickListener {
                cardLayoutTwo.background = null
                    cardLayoutOne.setBackgroundResource(R.drawable.card_view_style)
            }
            cardViewTwo.setOnClickListener {
                cardLayoutTwo.setBackgroundResource(R.drawable.card_view_style)
                cardLayoutOne.background = null
            }


        }
    }
}