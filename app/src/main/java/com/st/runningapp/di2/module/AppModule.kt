package com.st.runningapp.di2.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.st.runningapp.db.AppRunningDB
import com.st.runningapp.db.RunDAO
import com.st.runningapp.di2.annotations.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Named
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
}