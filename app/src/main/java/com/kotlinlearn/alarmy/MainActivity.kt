package com.kotlinlearn.alarmy

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlinlearn.alarmy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var alarmViewModel: AlarmViewModel
    private lateinit var alarmAdapter: AlarmRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alarmViewModel = ViewModelProvider(this)[AlarmViewModel::class.java]

        alarmViewModel.allAlarmsLiveData?.observe(this) {alarmList ->
            if (alarmList != null) {
                alarmAdapter.setData(alarmList)
            }
        }

        initView()
    }

    private fun initView() {
        binding.addBtn.setOnClickListener {
            startActivityForResult(Intent(this, AddAlarmActivity::class.java), requestCode)
        }
        setRV()
    }

    private fun setRV() {
        val rv = binding.alarmsRv
        val lm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        alarmAdapter = AlarmRecyclerAdapter(this, object: AlarmHelper {
            override fun updateAlarm(alarm: Alarm) {
                alarmViewModel.updateAlarm(alarm)
            }

        })
        rv.layoutManager = lm
        rv.adapter = alarmAdapter
    }

    override fun onStart() {
        super.onStart()
        alarmViewModel.getAllAlarms()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == MainActivity.requestCode) {
            var needRefresh = data?.extras?.getBoolean("needRefresh") as Boolean
            if (needRefresh) alarmViewModel.getAllAlarms()
        }
    }

    companion object {
        var activeAlarm: String? = ""
        val requestCode = 1000
    }
}