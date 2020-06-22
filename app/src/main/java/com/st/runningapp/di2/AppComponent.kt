package com.st.runningapp.di2

import android.app.Application
import com.st.runningapp.di2.annotations.ApplicationContext
import com.st.runningapp.di2.module.AppModule
import com.st.runningapp.repo.MainRepository
import com.st.runningapp.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    fun getMainRepository(): MainRepository

    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance @ApplicationContext application: Application): AppComponent
    }
}