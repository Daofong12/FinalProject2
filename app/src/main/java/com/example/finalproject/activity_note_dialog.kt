package com.example.finalproject

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import java.text.SimpleDateFormat
import java.util.*

class activity_note_dialog : AppCompatActivity() {
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_dialog)

        val date_text :TextView = findViewById(R.id.dialog_date)
        val btn_save: Button = findViewById(R.id.button_save)
        val microphone_btn: Button = findViewById(R.id.microphone_btn)
        val dialog_title: EditText = findViewById(R.id.dialog_title)//標題資訊
        val dialog_content: EditText = findViewById(R.id.dialog_content)//內容資訊



        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult? ->
            if (result!!.resultCode == RESULT_OK && result!!.data != null) {
                val speechtext: String? = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    .let { results -> results?.get(0) }
                var recTypeText:String = dialog_content.text.toString()
                recTypeText += speechtext.toString()
                dialog_content.setText(recTypeText)
            }
        }

        //TODO:整理日期、標題及內容三個進資料庫
        date_text.setText(getNow())//日期資訊

        microphone_btn.setOnClickListener {
            askSpeechInput()
        }

        btn_save.setOnClickListener {
            //關閉彈出視窗
            Toast.makeText(this, "儲存成功", Toast.LENGTH_SHORT).show()
            finish()
            //TODO:資料取得
        }
    }
    private fun askSpeechInput() {
        if(!SpeechRecognizer.isRecognitionAvailable(this)) {
            Toast.makeText(this, "Speech recognition is not available", Toast.LENGTH_SHORT).show()
        } else {
            val i = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.CHINESE)
            i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something")
            try {
                activityResultLauncher.launch(i)
            } catch (exp: ActivityNotFoundException) {
                Toast.makeText(applicationContext, "Device does not suppose", Toast.LENGTH_SHORT).show()
            }
        }
    }
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