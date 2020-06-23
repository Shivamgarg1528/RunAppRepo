package com.st.runningapp.ui.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.st.runningapp.db.LatLong
import com.st.runningapp.livedata.LocationLiveData
import com.st.runningapp.repo.MainRepository
import kotlinx.coroutines.launch

class TrackViewModel(application: Application, private val mainRepository: MainRepository) :
    AndroidViewModel(application) {

    var mLocationLiveData = LocationLiveData(application)
        private set

    fun getAllLatLong(): LiveData<List<LatLong>> = mainRepository.getAllLatLong()

    fun clearAllLatLong() {
        viewModelScope.launch {
            mainRepository.clearAllLatLong()
        }
    }
}