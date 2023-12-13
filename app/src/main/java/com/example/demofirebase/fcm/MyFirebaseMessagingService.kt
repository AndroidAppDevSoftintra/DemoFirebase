package com.example.demofirebase.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import com.example.demofirebase.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    lateinit var title : String
    lateinit var msg : String
    var Channel_Id = "my_channel_id"
    lateinit var manager: NotificationManager

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        println(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        println(message)
        title = message.notification!!.title!!
        msg = message.notification!!.body!!

        sendNotification(title,msg)
    }

    private fun sendNotification(title: String, msg: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =  NotificationChannel(Channel_Id,"test notification", NotificationManager.IMPORTANCE_HIGH)
            manager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            channel.apply {
                enableLights(true)
                enableVibration(true)
                setShowBadge(true)
                vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            }
            manager.createNotificationChannel(channel)
        }
        val builder = NotificationCompat.Builder(this,Channel_Id).apply {
            setContentText(msg)
            setContentTitle(title)
            setSmallIcon(R.mipmap.ic_launcher)
            setAutoCancel(true)
            setOngoing(true)
        }

        manager.notify(0,builder.build())
    }

}