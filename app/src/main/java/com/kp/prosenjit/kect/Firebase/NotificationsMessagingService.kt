package com.kp.prosenjit.kect.Firebase

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.app.NotificationManager
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.kp.prosenjit.kect.Activity.MainActivity
import com.kp.prosenjit.kect.R


class NotificationsMessagingService : FirebaseMessagingService(){

    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        Log.e("NotificationsService", "Token was updated==>"+token)

    }
    override fun onMessageReceived(rmessage: RemoteMessage?) {
        super.onMessageReceived(rmessage)
        Log.e("NotificationsService", "Got a remote message==>"+ rmessage!!.notification!!.title)
        Log.e("NotificationsService", "Got a body ==>"+ rmessage.notification!!.body)

        ShowBigTextStyleNotification(rmessage.notification!!.title,rmessage.notification!!.body)
    }

    private fun ShowBigTextStyleNotification(title: String?, body: String?) {
        val icon = R.drawable.kect_logo
        val mNotificationId = 1

        val mBuilder = NotificationCompat.Builder(
            this@NotificationsMessagingService
        )
        val notification = mBuilder.setSmallIcon(icon).setWhen(0)
            .setAutoCancel(false)
            .setContentTitle(title)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setLargeIcon(BitmapFactory.decodeResource(resources,icon))
            .setContentText(body).build()

        val notificationManager = this@NotificationsMessagingService.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(mNotificationId, notification)
    }
}