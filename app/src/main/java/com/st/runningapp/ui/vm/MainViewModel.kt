package com.st.runningapp.ui.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.st.runningapp.db.Run
import com.st.runningapp.repo.MainRepository
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application,
    private val mainRepository: MainRepository
) : AndroidViewModel(application) {

    fun iAmCalled() {
        Log.d("MainViewModel", "iAmCalled() called" + getApplication() + " .. " + mainRepository)
    }

    fun insertRun(run: Run) {
        viewModelScope.launch {
            mainRepository.insertRun(run)
        }
    }

    fun deleteRun(run: Run) {
        viewModelScope.launch {
            mainRepository.deleteRun(run)
        }
    }

    fun updateRun(run: Run) {
        viewModelScope.launch {
            mainRepository.updateRun(run)
        }
    }

    fun getAllRunsSortedByDate() = mainRepository.getAllRunsSortedByDate()

    fun getAllRunsSortedByTimeInMillis() = mainRepository.getAllRunsSortedByTimeInMillis()

    fun isUserDataAvailable() = mainRepository.isUserDataAvailable()

    fun saveUserInfo(userName: String, weight: Int) = mainRepository.saveUserInfo(userName, weight)

    fun clearUserInfo() = mainRepository.clearUserInfo()

    fun getName() = mainRepository.getName()

    fun getWeight() = mainRepository.getWeight()

}