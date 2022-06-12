package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class activity_checklist_dialog : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checklist_dialog)

        val pill_btn:Button = findViewById(R.id.button_pill)
        val diagnosis_btn:Button = findViewById(R.id.button_diagnosis)
        val diet_btn:Button = findViewById(R.id.button_diet)
        val care_btn:Button = findViewById(R.id.button_care)

        pill_btn.setOnClickListener(){
            StartChecklistDetailActivity("服藥")
        }
        diagnosis_btn.setOnClickListener(){
            StartChecklistDetailActivity("回診")
        }
        diet_btn.setOnClickListener(){
            StartChecklistDetailActivity("飲食")
        }
        care_btn.setOnClickListener(){
            StartChecklistDetailActivity("照護")
        }
    }
    private fun StartChecklistDetailActivity(category:String){
        val intent = Intent(this,activity_checklist_detail::class.java).apply{}
        intent.putExtra("category",category)
        startActivity(intent)
    }
}