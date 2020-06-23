package com.st.runningapp.ui.vm.factory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.st.runningapp.di2.annotations.ApplicationContext
import com.st.runningapp.repo.MainRepository
import javax.inject.Inject

class TrackViewModelFactory @Inject constructor(
    @ApplicationContext
    private val application: Application,
    private val mainRepository: MainRepository
) : ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        try {
            if (AndroidViewModel::class.java.isAssignableFrom(modelClass)) {
                return modelClass.getConstructor(
                    Application::class.java,
                    MainRepository::class.java
                ).newInstance(application, mainRepository)
            }
        } catch (e: Exception) {
        }
        return super.create(modelClass)
    }
}