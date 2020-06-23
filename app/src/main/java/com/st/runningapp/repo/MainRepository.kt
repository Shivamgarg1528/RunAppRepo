package com.st.runningapp.repo

import com.st.runningapp.db.Run
import com.st.runningapp.db.RunDAO
import com.st.runningapp.localstorage.AppSharedPreference
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val runDao: RunDAO,
    private val appSharedPreference: AppSharedPreference
) {

    suspend fun insertRun(run: Run) = runDao.insertRun(run)

    suspend fun deleteRun(run: Run) = runDao.deleteRun(run)

    suspend fun updateRun(run: Run) = runDao.updateRun(run)

    fun getAllRunsSortedByDate() = runDao.getAllRunsSortedByDate()

    fun getAllRunsSortedByDistance() = runDao.getAllRunsSortedByDistance()

    fun getAllRunsSortedByTimeInMillis() = runDao.getAllRunsSortedByTimeInMillis()

    fun getAllRunsSortedByAvgSpeed() = runDao.getAllRunsSortedByAvgSpeed()

    fun getAllRunsSortedByCaloriesBurned() = runDao.getAllRunsSortedByCaloriesBurned()

    fun getTotalAvgSpeed() = runDao.getTotalAvgSpeed()

    fun getTotalDistance() = runDao.getTotalDistance()

    fun getTotalCaloriesBurned() = runDao.getTotalCaloriesBurned()

    fun getTotalTimeInMillis() = runDao.getTotalTimeInMillis()

    // saving user data

    fun isUserDataAvailable(): Boolean {
        return getName().isBlank().not() && getWeight() != -1
    }

    fun saveUserInfo(userName: String, weight: Int) =
        appSharedPreference.saveUserInfo(userName, weight)

    fun getName() = appSharedPreference.getName()

    fun getWeight() = appSharedPreference.getWeight()
}