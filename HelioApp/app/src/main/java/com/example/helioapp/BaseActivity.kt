package com.example.helioapp

import android.app.Dialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.core.app.NotificationCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.helioapp.databinding.CustomProgressBarBinding
import com.example.helioapp.home_screen.HomeScreenActivity
import com.example.helioapp.utils.Constant
import com.example.helioapp.utils.Constant.CHANNEL_ID

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
            setCancelable(false)
            setContentView(binding.root)
            show()
        }
    }
    fun hideProgressBar() {
        progressBar.dismiss()
    }



}