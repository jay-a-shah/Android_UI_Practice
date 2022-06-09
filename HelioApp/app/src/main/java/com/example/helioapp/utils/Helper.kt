package com.example.helioapp.utils

import android.app.Activity
import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import android.text.*
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.content.res.ResourcesCompat
import com.example.helioapp.R

fun setUpPasswordToggle(context: Context, isPasswordHidden: Boolean, field: EditText) {
    if (isPasswordHidden) {
        field.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
    } else {
        field.inputType = InputType.TYPE_CLASS_TEXT
    }
    val typeface = ResourcesCompat.getFont(context, R.font.urbanist_semibold)
    field.typeface = typeface
}

fun isValidEmail(str: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(str).matches()
}

fun isValidPassword(str: String): Boolean {
    return TextUtils.isEmpty(str)
}

fun Activity.showMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun setSpannableText(text: String, startIndex: Int, endIndex: Int, color: Int, spanClickCallback: () -> Unit): Spannable {
    val spannable = SpannableString(text)
    val clickableSpan = object : ClickableSpan() {
        override fun onClick(p0: View) {
            spanClickCallback()
        }

        override fun updateDrawState(drawstate: TextPaint) {
            super.updateDrawState(drawstate)
            drawstate.isUnderlineText = false
            drawstate.color = color
        }
    }
    spannable.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return spannable
}