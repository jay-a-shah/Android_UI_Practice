package com.example.helioapp.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.helioapp.R
import com.example.helioapp.home_screen.HomeFragment
import com.example.helioapp.utils.Constant
import com.example.helioapp.utils.Constant.CHANNEL_ID
import com.example.helioapp.utils.Constant.GroupNotificationID
import com.example.helioapp.utils.Constant.NOTIFICATIONID
import com.example.helioapp.utils.Constant.SUMMARY_ID

class BaseNotificationHelper(private val context: Context): NotificationCompat.Builder(context,) {
    val resultIntent = Intent(context, HomeFragment::class.java)
    val pendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)

    fun createNotificationChannel() {
        var notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
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
        notificationManager.notify(NOTIFICATIONID, this.build())
    }

    fun groupNotification() {
        val newMessageNotificationOne = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_message)
            .setContentTitle("NewMessageNotification 1")
            .setContentText("You will not believe...")
            .setContentIntent(pendingIntent)
            .setGroup(GroupNotificationID)
            .build()
        val newMessageNotificationTwo = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_message)
            .setContentTitle("NewMessageNotification 2")
            .setContentText("Please join us to celebrate the...")
            .setContentIntent(pendingIntent)
            .setGroup(GroupNotificationID)
            .build()
        val newMessageNotificationThree = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_message)
            .setContentTitle("NewMessageNotification 3")
            .setContentText("Please join us to celebrate the...")
            .setContentIntent(pendingIntent)
            .setGroup(GroupNotificationID)
            .build()
        val newMessageNotificationFour = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_message)
            .setContentTitle("NewMessageNotification 4")
            .setContentText("Please join us to celebrate the...")
            .setContentIntent(pendingIntent)
            .setGroup(GroupNotificationID)
            .build()


        val summaryNotification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Summary Notification")
            //set content text to support devices running API level < 24
            .setContentText("Two new messages")
            .setSmallIcon(R.drawable.icon_apple)
            //build summary info into InboxStyle template
            .setStyle(
                NotificationCompat.InboxStyle()
                    .addLine("Alex Faarborg Check this out")
                    .addLine("Jeff Chang Launch Party")
                    .setBigContentTitle("25 new messages")
                    .setSummaryText("Jay Shah")
            )
            //specify which group this notification belongs to
            .setGroup(GroupNotificationID)
            //set this notification as the summary for the group
            .setGroupSummary(true)
            .build()

        NotificationManagerCompat.from(context).apply {
            notify(1, newMessageNotificationOne)
            notify(2, newMessageNotificationTwo)
            notify(3, newMessageNotificationThree)
            notify(4, newMessageNotificationFour)
            notify(5, summaryNotification)
        }
    }

    fun expandedNotification(icon: Int, title: String, content: String, largeIcon: Bitmap, largeText: String) {

        var notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setContentText(content)
            .setLights(Color.YELLOW, 1000, 300)
            .setLargeIcon(largeIcon)
            .setStyle(NotificationCompat.BigPictureStyle()
                .bigPicture(largeIcon)
                .bigLargeIcon(null))
           // .setStyle(NotificationCompat.BigTextStyle()
                //.bigText(largeText))
            .build()

        NotificationManagerCompat.from(context).apply {
            notify(6,notification)
        }
    }

}