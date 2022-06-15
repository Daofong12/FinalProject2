package com.example.finalproject

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.finalproject.util.Constants
import com.example.finalproject.util.RandomIntUtil

class AlarmService(private val context: Context) {
    private val alarmManager: AlarmManager? =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?

    fun setExactAlarm(timeInMillis: Long, id: Int) {
        setAlarm(
            timeInMillis,
            getPendingIntent(
                getIntent().apply {
                    action = Constants.ACTION_SET_EXACT_ALARM
//                    putExtra(Constants.EXTRA_EXACT_ALARM_TIME, timeInMillis)
                },
                id
            )
        )
    }

    fun cancelAlarm(id: Int) {
        alarmManager?.cancel(
            getPendingIntent(
                getIntent().apply {
                    action = Constants.ACTION_SET_EXACT_ALARM
    //                    putExtra(Constants.EXTRA_EXACT_ALARM_TIME, timeInMillis)
                },
                id
            )
        )
    }

    private fun setAlarm(timeInMillis: Long, pendingIntent: PendingIntent) {
        alarmManager?.let {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    timeInMillis,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    timeInMillis,
                    pendingIntent
                )
            }
        }
    }

    private fun getIntent(): Intent = Intent(context, AlarmReceiver::class.java)

    private fun getPendingIntent(intent: Intent, id: Int): PendingIntent =
        PendingIntent.getBroadcast(
            context,
            id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

}