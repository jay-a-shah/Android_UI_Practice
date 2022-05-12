package com.example.helioapp.utils

import android.content.Context
import android.text.InputType
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.content.res.ResourcesCompat
import com.example.helioapp.R

fun setUpPasswordToggle(context: Context, isPasswordHidden: Boolean, field: EditText, toggleButton: ImageButton, hasFocus: Boolean) {
    if (isPasswordHidden) {
        toggleButton.setImageResource(if (hasFocus) R.drawable.icon_open_green_eye else R.drawable.icon_open_eye)
        field.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
    } else {
        toggleButton.setImageResource(if (hasFocus) R.drawable.icon_green_hide else R.drawable.icon_eye_hide)
        field.inputType = InputType.TYPE_CLASS_TEXT
    }
    val typeface = ResourcesCompat.getFont(context, R.font.urbanist_semibold)
    field.typeface = typeface
}

