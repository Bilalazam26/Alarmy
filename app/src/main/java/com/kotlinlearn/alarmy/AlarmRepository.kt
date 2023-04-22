package com.kotlinlearn.alarmy

import android.content.Context
import androidx.lifecycle.MutableLiveData

class AlarmRepository(private val dao: AlarmDao?) {

    val allAlarmsLiveData = MutableLiveData<List<Alarm>>()
    var alarmMutableLiveData = MutableLiveData<Alarm>()

    suspend fun insertAlarm(alarm: Alarm) = dao?.insertAlarm(alarm)

    suspend fun updateAlarm(alarm: Alarm) = dao?.updateAlarm(alarm)

    suspend fun deleteAlarm(alarm: Alarm) = dao?.deleteAlarm(alarm)

    suspend fun getAlarm(id: Int) {
        alarmMutableLiveData.postValue(dao?.getAlarm(id))
    }

    suspend fun getAllAlarms() {
        allAlarmsLiveData.postValue(dao?.getAllAlarms())

    }

}