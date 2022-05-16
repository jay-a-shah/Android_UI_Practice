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
    var confirmPassword = ""
    var createPassword = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_new_password)
        createPasswordViewModel = ViewModelProvider(this)[CreatePasswordModel::class.java]
        binding.viewModel = createPasswordViewModel

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
            btnContinue.isEnabled = false
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
                if (btnContinue.isEnabled) {
                    val builder = AlertDialog.Builder(this@CreateNewPasswordActivity, R.style.CustomAlertDialog).create()
                    val view = layoutInflater.inflate(R.layout.custom_alert_dialog, null)
                    builder.setView(view)
                    val btnHomepage = view.findViewById<Button>(R.id.btnGoToHomepage)
                    btnHomepage.setOnClickListener {
                        finish()
                        startActivity(Intent(this@CreateNewPasswordActivity, MainActivity::class.java))
                    }
                    builder.setCanceledOnTouchOutside(false)
                    builder.show()
                } else {
                    Toast.makeText(this@CreateNewPasswordActivity,getString(R.string.toast_password_does_not_matches), Toast.LENGTH_SHORT).show()
                }

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
        createPasswordViewModel.createPassword.observe(this) {
            createPassword = it
            if (it.isEmpty()) {
                binding.btnContinue.isEnabled = false
            }
        }
        createPasswordViewModel.confirmPassword.observe(this) {
            confirmPassword = it
            if (it.isEmpty()) {
                binding.btnContinue.isEnabled = false
            } else {
                updateButtonUi()
            }
        }

    }

    private fun validationPassword() {
        binding.apply {

            if (createPasswordViewModel.confirmPassword.toString().isNotEmpty() && isValidPassword(editTextConfirmPassword.text.toString())
            ) {
                Toast.makeText(this@CreateNewPasswordActivity, getString(R.string.toast_confirm_password_valid), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@CreateNewPasswordActivity, getString(R.string.toast_confirm_password_invalid), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateButtonUi() {
        if (confirmPassword == createPassword) {
            if (confirmPassword.length >= 8 && createPassword.length >= 8) {
                Toast.makeText(this@CreateNewPasswordActivity, getString(R.string.toast_password_valid), Toast.LENGTH_SHORT).show()
                binding.btnContinue.setBackgroundResource(R.drawable.rounded_filled_button)
                binding.btnContinue.isEnabled = true
            } else {
                Toast.makeText(this@CreateNewPasswordActivity, getString(R.string.toast_password_invalid), Toast.LENGTH_SHORT).show()
            }
        } else {
            binding.btnContinue.setBackgroundResource(R.drawable.rounded_disable_button)
            binding.btnContinue.isEnabled = false
        }
    }
}