package com.st.runningapp.localstorage

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class AppSharedPreference @Inject constructor(private val sharedPreference: SharedPreferences) {

    fun saveUserInfo(userName: String, weight: Int) {
        sharedPreference.edit {
            putString("userName", userName)
            putInt("weight", weight)
        }
    }

    fun getName(): String {
        return sharedPreference.getString("userName", "")!!
    }

    fun getWeight(): Int {
        return sharedPreference.getInt("weight", -1)
    }
}