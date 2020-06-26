package com.st.runningapp.others

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import com.st.runningapp.R

object Util {
    fun getOrCreateNotification(context: Context) {
        val pendingIntent: PendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.trackingFragment)
            .setArguments(bundleOf("item1" to 1, "item2" to "2"))
            .createPendingIntent()

        val builder: Notification.Builder =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Notification.Builder(context, "Foreground Notification Test")
            } else {
                Notification.Builder(context)
            }
        builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.setContentIntent(pendingIntent)
        builder.setContentText("Test Notification")
        val notification = builder.build()
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(
            1,
            notification
        )
    }
}