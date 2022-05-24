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

fun Activity.showMessage(context: Context, message: String) {
    Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
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