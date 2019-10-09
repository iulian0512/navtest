package com.iulian.navtest

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.media.AudioAttributes
import android.os.Build
import android.provider.Settings
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.NotificationCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavDeepLinkBuilder


object NotificationHelper {

    fun createNotificationChannel(_app: Context, notif_channel_id: String) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val notificationManager = _app.getSystemService(NotificationManager::class.java)
            if (notificationManager?.getNotificationChannel(notif_channel_id) == null) {
                val importance = NotificationManager.IMPORTANCE_LOW
                val channel = NotificationChannel(notif_channel_id, "defchannelname", importance)
                channel.description = "descr"
                val abuilder = AudioAttributes.Builder()
                abuilder.setUsage(AudioAttributes.USAGE_NOTIFICATION)
                abuilder.setFlags(AudioAttributes.FLAG_AUDIBILITY_ENFORCED)

                channel.importance = NotificationManager.IMPORTANCE_HIGH
                channel.enableVibration(true)
                channel.enableLights(true)
                channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                channel.setSound(Settings.System.DEFAULT_NOTIFICATION_URI, abuilder.build())
                channel.vibrationPattern = longArrayOf(500, 0, 500, 0, 500)
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this

                notificationManager?.createNotificationChannel(channel)
            }
        }

    }

    fun deleteNotificationChannel(_app: Context, notif_channel_id: String) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = _app.getSystemService(NotificationManager::class.java)
            notificationManager?.deleteNotificationChannel(notif_channel_id)
        }

    }

    fun createnotification(
        ctx: Context,
        notif_channel_id: String,
        contentText: String,
        title: String
    ): Notification {

        //create notif channel for android api level 28+
        NotificationHelper.createNotificationChannel(ctx, notif_channel_id)


        val pendingIntent = NavDeepLinkBuilder(ctx)
            .setGraph(R.navigation.activity2_nav)
            .setComponentName(Activity2::class.java)
            .setDestination(R.id.fragment2)
            .createPendingIntent()


        //build the notification
        val notifBuilder = NotificationCompat.Builder(ctx, notif_channel_id)
            .setContentTitle(title)
            .setContentText(contentText)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_SOUND or NotificationCompat.DEFAULT_VIBRATE)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val notification = notifBuilder.build()

        return notification
    }

}

