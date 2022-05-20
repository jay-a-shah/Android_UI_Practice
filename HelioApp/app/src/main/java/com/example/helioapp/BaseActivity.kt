package com.example.helioapp

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.helioapp.databinding.CustomProgressBarBinding

open class BaseActivity : AppCompatActivity() {
    lateinit var progressBar: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_basic)
        progressBar = Dialog(this)
    }

    fun showProgressBar() {
        var binding:CustomProgressBarBinding = DataBindingUtil.inflate(LayoutInflater.from(this),R.layout.custom_progress_bar,null,false)
        progressBar.apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCancelable(true)
            setContentView(binding.root)
            show()
        }
    }
    fun hideProgressBar() {
        progressBar.dismiss()
    }
}