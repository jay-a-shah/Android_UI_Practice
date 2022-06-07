package com.example.helioapp.home_screen

import android.app.Notification
import android.app.Notification.EXTRA_NOTIFICATION_ID
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.helioapp.R
import com.example.helioapp.databinding.ActivityHomeScreenBinding
import com.example.helioapp.sign_in_screen.SignInActivity
import com.example.helioapp.utils.Constant
import com.example.helioapp.utils.Constant.CHANNEL_ID
import com.example.helioapp.utils.showMessage
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class HomeScreenActivity : AppCompatActivity() {
    lateinit var notificationManager: NotificationManager
    lateinit var binding: ActivityHomeScreenBinding
    private var homeFragment = HomeFragment()
    private var profileFragment = ProfileFragment()
    private var searchFragment = SearchFragment()
    private var bookingFragment = BookingFragment()
    lateinit var intentActivity: Intent
    lateinit var pendingIntent: PendingIntent
    val keyForReply = "key_text_reply"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_screen)
        binding.apply {
        }
        intentActivity = Intent(this, SignInActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        pendingIntent =
            PendingIntent.getActivity(this, 0, intentActivity, PendingIntent.FLAG_IMMUTABLE)
        createNotificationChannel()
        //setUpNotification()
        setBottomNavigation()
        remoteInput()
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d("token", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            Log.d("token", token)
            showMessage(this, token)
        })

    }

    fun setBottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> swapFragments(homeFragment)
                R.id.profile -> swapFragments(profileFragment)
                R.id.search -> swapFragments(searchFragment)
                R.id.booking -> swapFragments(bookingFragment)
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun swapFragments(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }

    fun setUpNotification() {
        val snoozeIntent = Intent(this, SignInActivity::class.java)
        val snoozePendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, snoozeIntent, PendingIntent.FLAG_IMMUTABLE)

        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("My notification")
            .setContentText("Much longer text that cannot fit one line...")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(false)
            .addAction(
                R.drawable.ic_notification, getString(R.string.btn_snooze),
                snoozePendingIntent
            )
            notificationManager.notify(123, builder.build())
    }


    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val name = "Notification"
            val descriptionText = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun remoteInput() {

        var replyLabel: String = getString(R.string.lbl_please_reply)
        val remoteInput: RemoteInput = RemoteInput.Builder(keyForReply)
            .run {
                setLabel("Write your message here")
                build()
            }

        // Build a PendingIntent for the reply action to trigger.
        var replyPendingIntent: PendingIntent = PendingIntent.getBroadcast(this, 0, intentActivity, PendingIntent.FLAG_UPDATE_CURRENT)
        // Create the reply action and add the remote input.
        var action: NotificationCompat.Action = NotificationCompat.Action.Builder(R.drawable.ic_search, replyLabel, replyPendingIntent)
                .addRemoteInput(remoteInput)
                .build()

        val newMessageNotification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_message)
            .setContentTitle("Reply Notification")
            .setContentText("You Can Reply into this Notification")
            .addAction(action)
            .build()

        notificationManager.notify(0,newMessageNotification)

    }


}




