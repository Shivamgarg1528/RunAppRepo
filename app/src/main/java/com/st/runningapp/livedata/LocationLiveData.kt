package com.st.runningapp.livedata

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.st.runningapp.di2.annotations.ApplicationContext
import com.st.runningapp.others.Util
import timber.log.Timber

class LocationLiveData(@ApplicationContext val applicationContext: Context) :
    LiveData<Location>() {

    private var permissionAvailable = false
    private var mFusedLocationClient =
        LocationServices.getFusedLocationProviderClient(applicationContext)

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return
            for (location in locationResult.locations) {
                setLocationData(location)
            }
        }
    }

    private fun setLocationData(location: Location) {
        value = location
    }

    override fun onActive() {
        super.onActive()
        Timber.i("onActive()")
        if (Util.checkLocationPermission(applicationContext)) {
            permissionAvailable = true
            mFusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.also {
                    setLocationData(it)
                }
            }
            mFusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    override fun onInactive() {
        super.onInactive()
        Timber.i("onInactive()")
        if (permissionAvailable) {
            mFusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }

    companion object {
        val locationRequest: LocationRequest = with(LocationRequest.create()) {
            interval = 5000
            fastestInterval = 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            this
        }
    }
}