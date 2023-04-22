package com.kotlinlearn.alarmy

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AlarmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(alarm: Alarm)

    @Update
    suspend fun updateAlarm(alarm: Alarm)

    @Delete
    suspend fun deleteAlarm(alarm: Alarm)

    @Query("SELECT * FROM Alarm")
    suspend fun getAllAlarms(): List<Alarm>

    @Query("SELECT * FROM Alarm WHERE id = :id")
    suspend fun getAlarm(id: Int): Alarm
}