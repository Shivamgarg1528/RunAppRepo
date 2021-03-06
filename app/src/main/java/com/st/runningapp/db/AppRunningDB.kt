package com.st.runningapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Run::class, LatLong::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(value = [Converters::class])
abstract class AppRunningDB : RoomDatabase() {
    abstract fun getRunDao(): RunDAO
    abstract fun getLatLongDao(): LatLongDAO
}