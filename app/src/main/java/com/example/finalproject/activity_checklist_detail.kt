package com.example.finalproject

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*

class activity_checklist_detail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checklist_detail)

        val calender = Calendar.getInstance()
        val tv_date:TextView = findViewById(R.id.textView_date)
        val tv_time:TextView = findViewById(R.id.textView_time)
        val tv_category:TextView = findViewById(R.id.textView_chk_category)
        var category:String = ""

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
            override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int){
                calender.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calender.set(Calendar.MINUTE, minute)
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

    }
}