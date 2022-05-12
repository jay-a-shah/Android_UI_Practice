package com.example.helioapp.utils

import android.content.Context
import android.text.InputType
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.content.res.ResourcesCompat
import com.example.helioapp.R

fun setUpPasswordToggle(context: Context, isPasswordHidden: Boolean, field: EditText, toggleButton: ImageButton) {
    if (isPasswordHidden) {
        toggleButton.setImageResource(R.drawable.open_eye_selector)
        field.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
    } else {
        toggleButton.setImageResource(R.drawable.close_eye_selector)
        field.inputType = InputType.TYPE_CLASS_TEXT
    }
    val typeface = ResourcesCompat.getFont(context, R.font.urbanist_semibold)
    field.typeface = typeface
}

