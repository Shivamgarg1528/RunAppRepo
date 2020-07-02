package com.st.runningapp

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.LifecycleService
import androidx.navigation.NavDeepLinkBuilder
import com.st.runningapp.livedata.LocationLiveData
import com.st.runningapp.others.Constant.ACTION_START_AND_RESUME_SERVICE
import com.st.runningapp.others.Constant.ACTION_STOP_SERVICE
import com.st.runningapp.others.Constant.CHANNEL_ID
import com.st.runningapp.others.Constant.NOTIFICATION_ID
import timber.log.Timber

class TrackingService : LifecycleService() {

    private lateinit var mLocationLiveData: LocationLiveData

    override fun onCreate() {
        super.onCreate()
        Timber.i("onCreate() called")
        mLocationLiveData = LocationLiveData(this)
        mLocationLiveData.observeForever {
            Timber.i("location data--> $it")
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Timber.i("onStartCommand() called with: intent = [$intent], flags = [$flags], startId = [$startId]")
        intent?.let {
            when (intent.action) {
                ACTION_START_AND_RESUME_SERVICE -> {
                    createNotificationChannel()
                    startForeground(NOTIFICATION_ID, getOrCreateNotification(this))
                }
                ACTION_STOP_SERVICE -> {
                    stopSelf()
                    Toast.makeText(this, "Service Stopped Successfully", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return Service.START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy() called")
    }

    companion object {
        fun sendCommand(context: Context, actionString: String) {
            with(Intent(context, TrackingService::class.java)) {
                action = actionString
                this
            }.also {
                context.startService(it)
            }
        }
    }

    private fun createNotificationChannel() {
        // creating default notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            (getSystemService(NotificationManager::class.java) as NotificationManager).createNotificationChannel(
                RunningApp.getAppComponent().getNotificationChannel()
            )
        }
    }

    private fun getOrCreateNotification(context: Context): Notification {
        // 1- will open tracking fragment
        val pendingIntent: PendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.trackingFragment)
            .setArguments(bundleOf("item1" to 1, "item2" to "2"))
            .createPendingIntent()

        // 2- close service action
        val serviceStopPendingIntent = PendingIntent.getService(
            this,
            0,
            Intent(this, TrackingService::class.java).also {
                it.action = ACTION_STOP_SERVICE
            },
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder: Notification.Builder =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Notification.Builder(context, CHANNEL_ID)
            } else {
                Notification.Builder(context)
            }
        builder.setAutoCancel(false)
        builder.setOngoing(true)
        builder.setSmallIcon(R.drawable.ic_run)
        builder.setContentIntent(pendingIntent)
        builder.addAction(
            Notification.Action.Builder(
                R.drawable.ic_close_white,
                "Stop",
                serviceStopPendingIntent
            ).build()
        )
        builder.setContentTitle("Tracking User Steps")
        builder.setContentText("00:00:00:00")
        return builder.build()
    }
}