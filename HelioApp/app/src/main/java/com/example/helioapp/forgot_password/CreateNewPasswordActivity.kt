package com.example.helioapp.forgot_password

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.view.marginStart
import androidx.databinding.DataBindingUtil
import com.example.helioapp.R
import com.example.helioapp.databinding.ActivityCreateNewPasswordBinding
import com.example.helioapp.databinding.CustomAlertDialogBinding

class CreateNewPasswordActivity : AppCompatActivity() {
    lateinit var binding: ActivityCreateNewPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_create_new_password)
        binding.apply {
            arrowImageView.setOnClickListener {
                finish()
            }
            btnContinue.setOnClickListener {
                val builder = AlertDialog.Builder(this@CreateNewPasswordActivity,R.style.CustomAlertDialog).create()
                val view = layoutInflater.inflate(R.layout.custom_alert_dialog,null)
                builder.setView(view)
                val btnHomepage = view.findViewById<Button>(R.id.btnGoToHomepage)
                btnHomepage.setOnClickListener {
                    builder.dismiss()
                }
                builder.setCanceledOnTouchOutside(false)
                builder.show()
            }
        }

    }
}