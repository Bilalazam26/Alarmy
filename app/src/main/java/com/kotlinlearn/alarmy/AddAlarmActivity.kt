package com.kotlinlearn.alarmy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.kotlinlearn.alarmy.databinding.ActivityAddAlarmBinding
import java.time.LocalDate
import java.util.Calendar

class AddAlarmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddAlarmBinding
    private lateinit var alarmViewModel: AlarmViewModel
    private lateinit var alarm: Alarm
    private var needRefresh = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alarmViewModel = ViewModelProvider(this)[AlarmViewModel::class.java]

        initView()
    }

    private fun initView() {
        binding.apply {
            saveBtn.setOnClickListener {
                val hour = timePicker.hour
                val minute = timePicker.minute
                val name = nameEt.text.toString()
                val calendar = Calendar.getInstance()
                calendar.set(LocalDate.now().year, LocalDate.now().monthValue, LocalDate.now().dayOfMonth, hour, minute)
                val alarm = Alarm(0, calendar.timeInMillis, name, true)
                alarmViewModel.insertAlarm(alarm)
                needRefresh = true
                onBackPressed()
            }
            cancelBtn.setOnClickListener {
                onBackPressed()
            }
        }
    }

    override fun finish() {
        val data = Intent()
        data.putExtra("needRefresh", needRefresh)
        this.setResult(RESULT_OK,data)
        super.finish()
    }
}