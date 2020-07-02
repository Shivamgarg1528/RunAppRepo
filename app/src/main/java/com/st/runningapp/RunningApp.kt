package com.st.runningapp

import android.app.Application
import android.app.NotificationManager
import android.os.Build
import com.st.runningapp.di2.AppComponent
import com.st.runningapp.di2.DaggerAppComponent
import com.st.runningapp.others.Util
import timber.log.Timber
import timber.log.Timber.DebugTree


class RunningApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree()) // enable timber in debug mode
        }
        mAppComponent = DaggerAppComponent.factory().create(this) // app component init
    }

    companion object {
        private lateinit var mAppComponent: AppComponent

        fun getAppComponent(): AppComponent {
            return mAppComponent
        }
    }
}