package com.example.helioapp.home_screen

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import com.example.helioapp.R
import com.example.helioapp.databinding.FragmentSearchBinding
import com.example.helioapp.notification.BaseNotificationHelper
import com.example.helioapp.sign_in_screen.SignInActivity
import com.example.helioapp.utils.Constant.CHANNEL_ID
import com.example.helioapp.utils.Constant.bundleKey
import com.example.helioapp.utils.Constant.keyForReply

class SearchFragment : Fragment(), View.OnClickListener {

    lateinit var notificationManager: NotificationManager
    lateinit var binding: FragmentSearchBinding
    lateinit var intentActivity: Intent
    var notificationId = 0
    lateinit var notification: BaseNotificationHelper
    private lateinit var bitmap: Bitmap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        createNotificationChannel()
        notification = BaseNotificationHelper(requireContext())
        intentActivity = Intent(requireContext(), SearchFragment::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        ContextCompat.getDrawable(requireContext(), R.drawable.icon_message_card)?.toBitmap()?.let {
            bitmap = it
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            clickHandler = this@SearchFragment
        }
        val bundle = arguments
        val notificationMessage = bundle?.getString(bundleKey)
        binding.tvNotificationReply.text = notificationMessage

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnGetNotification -> {
                remoteInput()
            }
            R.id.btnProgressNotification -> {
                startProgressBarNotification()
            }
            R.id.btnGroupedNotification -> {
                notification.groupNotification()
            }
            R.id.btnExpandedNotification -> {
                notification.expandedNotification(R.drawable.icon_splash,"Expandable Notification","Its Expanded Notification So It Will be Expanded on Swiping Down this Notification",bitmap,getString(
                                    R.string.longText))
            }
        }
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
            notificationManager = requireContext().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
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
        val newIntent = Intent(requireContext(),HomeScreenActivity::class.java)
        // Build a PendingIntent for the reply action to trigger.
        var replyPendingIntent: PendingIntent = PendingIntent.getActivity(requireContext(), 0, newIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        // Create the reply action and add the remote input.
        var action: NotificationCompat.Action = NotificationCompat.Action.Builder(R.drawable.ic_search, replyLabel, replyPendingIntent)
                .addRemoteInput(remoteInput)
                .setAllowGeneratedReplies(true)
                .build()
        //Set Reply From Notification

        val newMessageNotification = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Reply Notification")
            .setContentText("You Can Reply into this Notification")
            .addAction(action)
            .build()
        notificationManager.notify(notificationId, newMessageNotification)
    }


    fun startProgressBarNotification() {
        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setContentTitle(getString(R.string.title_dowload_in_progress))
            .setContentText(getString(R.string.text_downloading))
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .setSmallIcon(R.drawable.ic_notification)
            .setNumber(5)
            .setPriority(NotificationCompat.PRIORITY_LOW)

        val maxProgress = 100
        var currentProgress = 0
        NotificationManagerCompat.from(requireContext()).apply {
            // Issue the initial notification with zero progress
            builder.setProgress(maxProgress, currentProgress, false)
            notify(notificationId, builder.build())
            Thread(Runnable{
               // SystemClock.sleep(2000)
                while (currentProgress < maxProgress) {
                    SystemClock.sleep(
                        1000
                    )
                    currentProgress += 5
                    //Use this to make it a Fixed-duration progress indicator notification
                    builder.apply {
                        setContentText("$currentProgress%")
                        setProgress(maxProgress, currentProgress, false)
                    }
                  notify(notificationId, builder.build())
                }
                builder.apply {
                    setContentText(getString(R.string.text_download_complete))
                    setProgress(0, 0, false)
                    setOngoing(false)
                }
              notify(notificationId, builder.build())
            }).start()
        }
    }
}
