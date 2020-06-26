package com.st.runningapp.di2

import android.app.Application
import android.app.NotificationChannel
import com.st.runningapp.di2.annotations.ApplicationContext
import com.st.runningapp.di2.module.AppModule
import com.st.runningapp.repo.MainRepository
import com.st.runningapp.ui.MainActivity
import com.st.runningapp.ui.fragments.TrackingFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    fun getMainRepository(): MainRepository

    fun getNotificationChannel(): NotificationChannel

    fun inject(mainActivity: MainActivity)
    fun inject(trackingFragment: TrackingFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance @ApplicationContext application: Application): AppComponent
    }
}