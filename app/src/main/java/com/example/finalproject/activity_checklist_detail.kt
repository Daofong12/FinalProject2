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

class activity_checklist_detail : AppCompatActivity() {

    private lateinit var sqliteHelper_c: SQLiteHelper_c
    lateinit var alarmService: AlarmService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checklist_detail)

        val calender = Calendar.getInstance()
        val tv_date:TextView = findViewById(R.id.textView_date)
        val tv_time:TextView = findViewById(R.id.textView_time)
        val tv_category:TextView = findViewById(R.id.textView_chk_category)
        var category:String = ""
        val dialog_event:EditText = findViewById(R.id.text_checklist_event)
        val dialog_location:EditText = findViewById(R.id.text_checklist_location)
        val btn_chk_save:Button = findViewById(R.id.button_chk_save)

        alarmService = AlarmService(this)

        if(intent.getStringExtra("category").toString()=="服藥"){
            tv_category.setText("服藥")
            category = "medicine"
        }
        else if(intent.getStringExtra("category").toString()=="回診"){
            tv_category.setText("回診")
            category = "diagnosis"
        }
        else if(intent.getStringExtra("category").toString()=="飲食"){
            tv_category.setText("飲食")
            category = "care"
        }
        else{
            tv_category.setText("照護")
            category = "care"
        }

        val listener = object :DatePickerDialog.OnDateSetListener{
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
                calender.set(year,month,day)
                val myformat = "yyyy-MM-dd"
                val sdf = SimpleDateFormat(myformat, Locale.TAIWAN)
                tv_date.text = sdf.format(calender.time)
            }
        }
        val img_calender:ImageView = findViewById(R.id.image_calender)
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
                val time = android.icu.text.SimpleDateFormat(myformat, Locale.TAIWAN)
                tv_time.text = time.format(calender.time)
            }
        }
        val img_time:ImageView = findViewById(R.id.image_time)
        img_time.setOnClickListener{
            TimePickerDialog(this,timeListener,
                calender.get(Calendar.HOUR_OF_DAY),calender.get(Calendar.MINUTE),true).show()
        }

        sqliteHelper_c = SQLiteHelper_c(this)
        btn_chk_save.setOnClickListener{
            addChecklist(tv_date.text.toString(),tv_time.text.toString(),category,
                dialog_event.text.toString(),dialog_location.text.toString())
            clearText(tv_date,tv_time,tv_category,dialog_event,dialog_location)

            alarmService.setExactAlarm(calender.timeInMillis)
            finish()
        }

    }
    private fun addChecklist(date_c:String,time:String,category:String,event:String,location:String) {
        if(date_c.isEmpty() || time.isEmpty() || category.isEmpty() || event.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Please enter required field", Toast.LENGTH_SHORT).show()
        }else{
            val checklist = ChecklistModel(date_c = date_c, time = time, category = category,
            event = event, location = location)
            val status = sqliteHelper_c.insertChecklist(checklist)
            if (status > -1){
                Toast.makeText(this, "Checklist added...", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, "Checklist not saved", Toast.LENGTH_SHORT).show()
            }
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