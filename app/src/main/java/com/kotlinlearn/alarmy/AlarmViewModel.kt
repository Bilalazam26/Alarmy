package com.kotlinlearn.alarmy

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmViewModel(application: Application): AndroidViewModel(application) {

    private val repository: AlarmRepository
    val allAlarmsLiveData: LiveData<List<Alarm>>?
    val alarmLiveData: LiveData<Alarm>

    init {
        val dao = AlarmDatabase.alarmDatabaseInstance(application)?.AlarmDao()
        repository = AlarmRepository(dao)
        allAlarmsLiveData = repository.allAlarmsLiveData
        alarmLiveData = repository.alarmMutableLiveData

    }


    fun insertAlarm(alarm: Alarm) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertAlarm(alarm)
    }

    fun updateAlarm(alarm: Alarm) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateAlarm(alarm)
    }

    fun deleteAlarm(alarm: Alarm) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAlarm(alarm)
    }

    fun getAlarm(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.getAlarm(id)
    }

    fun getAllAlarms() = viewModelScope.launch(Dispatchers.IO) {
        repository.getAllAlarms()
    }
}