package com.kotlinlearn.alarmy

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Alarm::class], version = 1)
abstract class AlarmDatabase : RoomDatabase() {
    abstract fun AlarmDao(): AlarmDao

    companion object {
        private var instance: AlarmDatabase? = null
        fun alarmDatabaseInstance(context: Context): AlarmDatabase? {
            if (instance == null) {
                synchronized(AlarmDatabase::class) {
                    instance = buildRoomDb(context)
                }
            }
            return instance
        }

        private fun buildRoomDb(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AlarmDatabase::class.java,
                "AlarmDatabase"
            ).build()
    }
}