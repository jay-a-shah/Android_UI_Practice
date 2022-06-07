package com.example.helioapp.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.helioapp.utils.Constant

class BaseNotificationHelper(private val context: Context): NotificationCompat.Builder(context,) {

    fun createNotificationChannel() {
        var notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification"
            val descriptionText = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(Constant.CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(123, this.build())
    }
}