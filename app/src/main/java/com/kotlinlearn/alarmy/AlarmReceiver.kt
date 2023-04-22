package com.kotlinlearn.alarmy

import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.content.Intent

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        var isRunning = false
        var str = intent?.extras?.getString("extra")

        val activityManager = context?.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        for (service in activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if (Music::class.java.name == service.service.className) {
                isRunning = true

            }
        }
        val mIntent = Intent(context, Music::class.java)
        if (str.equals("on")) {
            context.startService(mIntent)
            MainActivity.activeAlarm = intent?.extras?.getString("active")
        }else if (str.equals("off")){
            context.startService(mIntent)
            MainActivity.activeAlarm = ""
        }

    }
}