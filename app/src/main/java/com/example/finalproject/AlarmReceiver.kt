package com.example.finalproject

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.icu.text.CaseMap
import android.icu.text.DateFormat
import android.icu.text.MessageFormat.format
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.finalproject.util.Constants
import io.karn.notify.Notify
import java.util.*


class AlarmReceiver: BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onReceive(context: Context, intent: Intent) {
        val timeInMillis = intent.getLongExtra(Constants.EXTRA_EXACT_ALARM_TIME, 0L)

        when (intent.action) {
            Constants.ACTION_SET_EXACT_ALARM -> {
                buildNotification(context, "待辦事項", "記得去做!!!!!!")
            }
        }

    }

    private fun buildNotification(context: Context, title: String, message: String) {
        Notify
            .with(context)
            .content {
                this.title = title
                this.text = message
            }
            .show()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun convertDate(timeInMillis: Long): String {
        val myformat_d = "yyyy-MM-dd"
        val myformat_t = "hh:mm"
        val sdf_d = SimpleDateFormat(myformat_d, Locale.TAIWAN).toString()
        val sdf_t = SimpleDateFormat(myformat_t, Locale.TAIWAN).toString()
        return sdf_d+sdf_t
    }



}