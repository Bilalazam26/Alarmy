package com.kotlinlearn.alarmy

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kotlinlearn.alarmy.databinding.AlarmLayoutBinding
import java.util.Calendar

class AlarmRecyclerAdapter(private val context: Context?, private val alarmHelper: AlarmHelper) :
    RecyclerView.Adapter<AlarmRecyclerAdapter.AlarmViewHolder>() {
    private var AlarmList: MutableList<Alarm?> = mutableListOf<Alarm?>()

    inner class AlarmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val view = itemView
        val binding = AlarmLayoutBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.alarm_layout, parent, false)
        return AlarmViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        var alarm = AlarmList[position]
        val serviceIntent = Intent(context, AlarmReceiver::class.java)
        val alarmManager = context?.getSystemService(ALARM_SERVICE) as AlarmManager
        val calendar = Calendar.getInstance()
        holder.binding.apply {
            nameTv.text = alarm?.name
            timeTv.text = alarm?.time.toString()

            calendar.timeInMillis = alarm?.time as Long
            if (calendar.timeInMillis < System.currentTimeMillis()) {
                calendar.add(Calendar.DATE, 1)
            }
            onOffBtn.isChecked = alarm.state
            onOffBtn.setOnCheckedChangeListener { _, isChecked ->
                alarm.state = isChecked
                alarmHelper.updateAlarm(alarm)
                notifyItemChanged(position)

                if (!isChecked) {
                    serviceIntent.putExtra("extra", "off")
                    val pendingIntent = PendingIntent.getBroadcast(context, position, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT)
                    alarmManager.cancel(pendingIntent)
                    context.sendBroadcast(serviceIntent)
                }
            }
        }
        if (alarm?.state as Boolean) {
            serviceIntent.putExtra("extra", "on")
            serviceIntent.putExtra("active", "${alarm.time}")
            val pendingIntent = PendingIntent.getBroadcast(context, position, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        }
    }

    override fun getItemCount(): Int {
        return AlarmList.size
    }

    fun setData(AlarmList: List<Alarm?>) {
        this.AlarmList.addAll(AlarmList)
        notifyDataSetChanged() //to notify adapter that new data change has been happened to adapt it
    }
}