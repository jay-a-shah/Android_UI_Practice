package com.example.helioapp.home_screen

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import com.example.helioapp.R
import com.example.helioapp.databinding.FragmentSearchBinding
import com.example.helioapp.sign_in_screen.SignInActivity
import com.example.helioapp.utils.Constant
import com.example.helioapp.utils.Constant.CHANNEL_ID

class SearchFragment : Fragment(),View.OnClickListener{

    lateinit var notificationManager: NotificationManager
    lateinit var binding: FragmentSearchBinding
    val keyForReply = "key_text_reply"
    lateinit var intentActivity: Intent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search,container, false)
        createNotificationChannel()

        intentActivity = Intent(requireContext(), SearchFragment::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        handleIntent()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            clickHandler = this@SearchFragment
        }

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btnGetNotification -> {
                remoteInput()
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

        // Build a PendingIntent for the reply action to trigger.
        var replyPendingIntent: PendingIntent = PendingIntent.getActivity(requireContext(), 0, intentActivity, PendingIntent.FLAG_UPDATE_CURRENT)
        // Create the reply action and add the remote input.
        var action: NotificationCompat.Action = NotificationCompat.Action.Builder(R.drawable.ic_search, replyLabel, replyPendingIntent)
            .addRemoteInput(remoteInput)
            .build()
        //Set Reply From Notification

        val newMessageNotification = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Reply Notification")
            .setContentText("You Can Reply into this Notification")
            .addAction(action)
            .build()
        notificationManager.notify(0,newMessageNotification)


    }


    private fun handleIntent() {
        val intent = this.intentActivity
        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        if (remoteInput != null) {
            val inputString = remoteInput.getCharSequence(keyForReply).toString()
            binding.tvNotificationReply.text = inputString
        }
    }


}