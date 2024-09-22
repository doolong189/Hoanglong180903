package com.hoanglong180903.vnc_project.firebase

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.hoanglong180903.vnc_project.ListActivity
import com.hoanglong180903.vnc_project.R

class MyFirebaseMessing : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.e("token",token.toString())
    }

    override fun onMessageReceived(message: RemoteMessage) {
        if (message.notification != null){
            val title : String = message.notification?.title!!
            val body : String = message.notification?.body!!
            addNotification(title, body)
        }
    }

    private fun addNotification(title :String , body:String){
        val builder: Notification.Builder? = Notification.Builder(this)
            .setSmallIcon(R.drawable.baseline_commute_24)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setPriority(Notification.PRIORITY_DEFAULT)
        val notificationIntent = Intent(this,ListActivity::class.java)
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        notificationIntent.putExtra("message",body)
        val pendingIntent = PendingIntent.getActivity(this,0,
            notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT)
        builder?.setContentIntent(pendingIntent)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager
        manager.notify(0,builder?.build())
    }
}