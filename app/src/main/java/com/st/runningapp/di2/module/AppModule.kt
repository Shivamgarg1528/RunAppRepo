package com.st.runningapp.di2.module

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.st.runningapp.db.AppRunningDB
import com.st.runningapp.db.LatLongDAO
import com.st.runningapp.db.RunDAO
import com.st.runningapp.di2.annotations.ApplicationContext
import com.st.runningapp.others.Constant.CHANNEL_ID
import com.st.runningapp.others.Constant.CHANNEL_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideAppSharedPreference(@ApplicationContext application: Application): SharedPreferences {
        return application.getSharedPreferences("app-preference", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext application: Application): AppRunningDB {
        return Room.databaseBuilder(
            application,
            AppRunningDB::class.java,
            "running_d1.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDatabaseRunDAO(appRunningDB: AppRunningDB): RunDAO {
        return appRunningDB.getRunDao()
    }

    @Provides
    @Singleton
    fun provideDatabaseLatLongDAO(appRunningDB: AppRunningDB): LatLongDAO {
        return appRunningDB.getLatLongDao()
    }

    @Provides
    @RequiresApi(Build.VERSION_CODES.O)
    fun provideDefaultNotificationChannel(): NotificationChannel {
        return NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
    }
}