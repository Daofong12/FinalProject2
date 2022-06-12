package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class record_checklist : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_checklist)

        val date_text:TextView = findViewById(R.id.textView_checklist_date)
        date_text.setText(getNow())

        val fab_note: View = findViewById(R.id.FAB_checklist)
        fab_note.setOnClickListener {
            val intent = Intent(this,activity_checklist_dialog::class.java).apply{}
            startActivity(intent)
        }
    }
    //Maybe Change by Calender
    private fun getNow(): String {
        if (android.os.Build.VERSION.SDK_INT >= 24) {
            return SimpleDateFormat("yyyy-MM-dd").format(Date())
        } else {
            var tms = Calendar.getInstance()
            return tms.get(Calendar.YEAR).toString() + "-" + tms.get(Calendar.MONTH)
                .toString() + "-" + tms.get(Calendar.DAY_OF_MONTH).toString()
        }
    }
}