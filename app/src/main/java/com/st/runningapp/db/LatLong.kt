package com.st.runningapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lat_long_table")
data class LatLong(
    @PrimaryKey(autoGenerate = true) var rowId: Int? = null,
    var timestamp: Long = System.currentTimeMillis(),
    var longitude: Double = 0.0,
    var latitude: Double = 0.0
)
