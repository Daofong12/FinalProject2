package com.example.finalproject

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*

class activity_update_checklist : AppCompatActivity() {

    private lateinit var sqliteHelper_c: SQLiteHelper_c
    lateinit var alarmService: AlarmService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_checklist)

        val id_c = intent.getIntExtra("id_c", 0)
        val date_c = intent.getStringExtra("date_c")
        val time = intent.getStringExtra("time")
        val category = intent.getStringExtra("category")
        val event = intent.getStringExtra("event")
        val location = intent.getStringExtra("location")
        val isSelected = intent.getIntExtra("isSelected",0)

        val calender = Calendar.getInstance()
        val tv_date: TextView = findViewById(R.id.textView_date_update)
        val tv_time: TextView = findViewById(R.id.textView_time_update)
        val tv_category: TextView = findViewById(R.id.textView_chk_category_update)
        val dialog_event: EditText = findViewById(R.id.text_checklist_event_update)
        val dialog_location: EditText = findViewById(R.id.text_checklist_location_update)
        val btn_chk_update: Button = findViewById(R.id.button_chk_update)

        alarmService = AlarmService(this)

        if(category=="服藥"){
            tv_category.setText("服藥")
        }
        else if(category=="回診"){
            tv_category.setText("回診")
        }
        else if(category=="飲食"){
            tv_category.setText("飲食")
        }
        else{
            tv_category.setText("照護")
        }

        tv_date.setText(date_c)
        tv_time.setText(time)
        dialog_event.setText(event)
        dialog_location.setText(location)

        val listener = object : DatePickerDialog.OnDateSetListener{
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
                calender.set(year,month,day)
                val myformat = "yyyy-MM-dd"
                val sdf = SimpleDateFormat(myformat, Locale.TAIWAN)
                tv_date.text = sdf.format(calender.time)
            }
        }
        val img_calender: ImageView = findViewById(R.id.image_calender_update)
        img_calender.setOnClickListener{
            DatePickerDialog(this,listener,
                calender.get(Calendar.YEAR),calender.get(Calendar.MONTH),calender.get(Calendar.DAY_OF_MONTH)).show()
        }

        val timeListener = object: TimePickerDialog.OnTimeSetListener {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int){
                calender.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calender.set(Calendar.MINUTE, minute)
                calender.set(Calendar.SECOND, 0)
                calender.set(Calendar.MILLISECOND, 0)
                val myformat = "HH : mm"
                val time_c = android.icu.text.SimpleDateFormat(myformat, Locale.TAIWAN)
                tv_time.text = time_c.format(calender.time)
            }
        }
        val img_time: ImageView = findViewById(R.id.image_time_update)
        img_time.setOnClickListener{
            TimePickerDialog(this,timeListener,
                calender.get(Calendar.HOUR_OF_DAY),calender.get(Calendar.MINUTE),true).show()
        }

        sqliteHelper_c = SQLiteHelper_c(this)
        btn_chk_update.setOnClickListener{
            val new_date_c = tv_date.text.toString()
            val new_time = tv_time.text.toString()
            val new_event = dialog_event.text.toString()
            val new_location =dialog_location.text.toString()

            if(new_date_c == date_c && new_time == time && new_event == event && new_location ==location) {
                Toast.makeText(this, "Not changed...", Toast.LENGTH_SHORT).show()
            }

            val checklist = ChecklistModel(id_c = id_c,date_c = new_date_c, time = new_time, category = category.toString(),
                event = new_event, location = new_location, isSelected = isSelected)
            val status = sqliteHelper_c.updateChecklist(checklist)
            if(status > -1){
                clearText(tv_date,tv_time,tv_category,dialog_event,dialog_location)
            }else{
                Toast.makeText(this, "Update failed...", Toast.LENGTH_SHORT).show()
            }

            alarmService.setExactAlarm(calender.timeInMillis)

            finish()
        }
    }

    private fun clearText(tv_date:TextView,tv_time:TextView,tv_category:TextView,dialog_event:EditText,dialog_location:EditText) {
        tv_date.text = ""
        tv_time.text = ""
        tv_category.text = ""
        dialog_event.setText("")
        dialog_location.setText("")
    }
}