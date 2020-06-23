package com.st.runningapp.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface LatLongDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLatLong(latLong: LatLong)

    @Delete
    suspend fun deleteLatLong(latLong: LatLong)

    @Update
    suspend fun updateLatLong(latLong: LatLong)

    @Query("DELETE FROM lat_long_table")
    suspend fun clearAllLatLong()

    @Query("Select * from lat_long_table ORDER BY timestamp DESC")
    fun getAllLatLong(): LiveData<List<LatLong>>
}