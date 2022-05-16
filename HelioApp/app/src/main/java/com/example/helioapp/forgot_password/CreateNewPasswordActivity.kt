package com.example.helioapp.forgot_password

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.marginStart
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.helioapp.MainActivity
import com.example.helioapp.R
import com.example.helioapp.databinding.ActivityCreateNewPasswordBinding
import com.example.helioapp.databinding.CustomAlertDialogBinding
import com.example.helioapp.sign_in_screen.SignInViewModel
import com.example.helioapp.utils.isValidPassword
import com.example.helioapp.utils.setUpPasswordToggle

class CreateNewPasswordActivity : AppCompatActivity() {
    lateinit var binding: ActivityCreateNewPasswordBinding
    var isPasswordHidden: Boolean = true
    private lateinit var createPasswordViewModel: CreatePasswordModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_new_password)
        createPasswordViewModel = ViewModelProvider(this)[CreatePasswordModel::class.java]
        binding.viewModel = createPasswordViewModel
        updateButtonUi()
        setUpPasswordToggle(
            this,
            isPasswordHidden,
            binding.editTextCreatePassword,
            binding.imageBtnEyeCreatePassword,
            false
        )
        setUpPasswordToggle(
            this,
            isPasswordHidden,
            binding.editTextConfirmPassword,
            binding.imageBtnEyeConfirmPassword,
            false
        )
        binding.apply {
            arrowImageView.setOnClickListener {
                finish()
            }
            editTextCreatePassword.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        setUpPasswordToggle(
                            this@CreateNewPasswordActivity,
                            isPasswordHidden,
                            editTextCreatePassword,
                            imageBtnEyeCreatePassword,
                            hasFocus
                        )
                    } else {
                        validationPassword()
                        setUpPasswordToggle(
                            this@CreateNewPasswordActivity,
                            isPasswordHidden,
                            editTextCreatePassword,
                            imageBtnEyeCreatePassword,
                            hasFocus
                        )
                    }
                }

            editTextConfirmPassword.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        setUpPasswordToggle(
                            this@CreateNewPasswordActivity,
                            isPasswordHidden,
                            editTextConfirmPassword,
                            imageBtnEyeConfirmPassword,
                            hasFocus
                        )
                    } else {
                        validationPassword()
                        setUpPasswordToggle(
                            this@CreateNewPasswordActivity,
                            isPasswordHidden,
                            editTextConfirmPassword,
                            imageBtnEyeConfirmPassword,
                            hasFocus
                        )
                    }
                }
            btnContinue.setOnClickListener {
                val builder =
                    AlertDialog.Builder(this@CreateNewPasswordActivity, R.style.CustomAlertDialog)
                        .create()
                val view = layoutInflater.inflate(R.layout.custom_alert_dialog, null)
                builder.setView(view)
                val btnHomepage = view.findViewById<Button>(R.id.btnGoToHomepage)
                btnHomepage.setOnClickListener {
                    finish()
                    startActivity(Intent(this@CreateNewPasswordActivity, MainActivity::class.java))
                }
                builder.setCanceledOnTouchOutside(false)
                builder.show()
            }
            imageBtnEyeCreatePassword.setOnClickListener {
                it.isSelected = !it.isSelected
                isPasswordHidden = !isPasswordHidden
                setUpPasswordToggle(
                    this@CreateNewPasswordActivity,
                    isPasswordHidden,
                    binding.editTextCreatePassword,
                    imageBtnEyeCreatePassword,
                    editTextCreatePassword.hasFocus()
                )
            }
            imageBtnEyeConfirmPassword.setOnClickListener {
                it.isSelected = !it.isSelected
                isPasswordHidden = !isPasswordHidden
                setUpPasswordToggle(
                    this@CreateNewPasswordActivity,
                    isPasswordHidden,
                    binding.editTextConfirmPassword,
                    imageBtnEyeConfirmPassword,
                    editTextConfirmPassword.hasFocus()
                )
            }
        }
    }

    private fun validationPassword() {
        binding.apply {
            if (createPasswordViewModel.createPassword.toString().isNotEmpty() && isValidPassword(
                    editTextCreatePassword.text.toString()
                )
            ) {
                Toast.makeText(
                    this@CreateNewPasswordActivity,
                    getString(R.string.toast_password_valid),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this@CreateNewPasswordActivity,
                    getString(R.string.toast_password_invalid),
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (createPasswordViewModel.confirmPassword.toString().isNotEmpty() && isValidPassword(
                    editTextConfirmPassword.text.toString()
                )
            ) {
                Toast.makeText(
                    this@CreateNewPasswordActivity,
                    getString(R.string.toast_password_valid),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this@CreateNewPasswordActivity,
                    getString(R.string.toast_password_invalid),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun updateButtonUi() {
        binding.apply {
            if (editTextCreatePassword.text.toString() == editTextConfirmPassword.text.toString()) {
                binding.btnContinue.setBackgroundResource(R.drawable.rounded_filled_button)
            } else {
                btnContinue.setBackgroundResource(R.drawable.rounded_disable_button)
            }
        }
    }
}