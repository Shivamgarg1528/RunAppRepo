package com.st.runningapp

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.observe
import com.st.runningapp.livedata.LocationLiveData
import timber.log.Timber

class LocationService : LifecycleService() {

    private lateinit var mLocationLiveData: LocationLiveData

    override fun onCreate() {
        super.onCreate()
        Timber.i("onCreate() called")
        mLocationLiveData = LocationLiveData(this)
        mLocationLiveData.observeForever {
            Timber.i("location data--> $it")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy() called")
    }

    companion object {

        fun start(context: Context) {
            with(Intent(context, LocationService::class.java)) {
                this
            }.also {
                context.startService(it)
            }
        }

        fun stop(context: Context) {
            with(Intent(context, LocationService::class.java)) {
                this
            }.also {
                context.stopService(it)
            }
        }
    }
}