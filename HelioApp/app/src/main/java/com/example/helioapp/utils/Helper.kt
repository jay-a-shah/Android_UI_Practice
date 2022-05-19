package com.example.helioapp.utils

import android.content.Context
import android.text.InputType
import android.text.TextUtils
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.example.helioapp.R

fun setUpPasswordToggle(context: Context, isPasswordHidden: Boolean, field: EditText, toggleButton: ImageButton, hasFocus: Boolean) {
    if (isPasswordHidden) {
        toggleButton.setImageResource(if (hasFocus) R.drawable.icon_green_hide else R.drawable.ic_hide)
        field.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
    } else {
        toggleButton.setImageResource(if (hasFocus) R.drawable.icon_open_green_eye else R.drawable.ic_show)
        field.inputType = InputType.TYPE_CLASS_TEXT
    }
    field.setSelection(field.text.length)
    val typeface = ResourcesCompat.getFont(context, R.font.urbanist_semibold)
    field.typeface = typeface
}

fun isValidEmail(str: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(str).matches()
}
fun isValidPassword(str: String): Boolean {
    return TextUtils.isEmpty(str)
}
fun showMessage(context:Context,message: String) {
    Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
}