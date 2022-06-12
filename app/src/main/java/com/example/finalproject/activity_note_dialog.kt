package com.example.finalproject

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import java.text.SimpleDateFormat
import java.util.*

class activity_note_dialog : AppCompatActivity() {
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var sqliteHelper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_dialog)

        val date_text :TextView = findViewById(R.id.dialog_date_update)
        val btn_save: Button = findViewById(R.id.button_update)
        val microphone_btn: Button = findViewById(R.id.microphone_btn_update)
        val dialog_title: EditText = findViewById(R.id.dialog_title_update)//標題資訊
        val dialog_content: EditText = findViewById(R.id.dialog_content_update)//內容資訊

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

        sqliteHelper = SQLiteHelper(this)

        btn_save.setOnClickListener {
            //Note存進資料庫
            addNote(date_text.text.toString(),dialog_title.text.toString(),dialog_content.text.toString())
            clearEditText(dialog_title,dialog_content)

            //關閉彈出視窗
            Toast.makeText(this, "儲存成功", Toast.LENGTH_SHORT).show()
            finish()
            //TODO:資料取得
        }
    }

    private fun addNote(date:String,title:String,content:String) {
        if(date.isEmpty() || title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Please enter required field", Toast.LENGTH_SHORT).show()
        }else{
            val note = NoteModel(date = date, subject = title, content = content)
            val status = sqliteHelper.insertNote(note)
            if (status > -1){
                Toast.makeText(this, "Note added...", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, "Record not saved", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun clearEditText(dialog_title:EditText,dialog_content:EditText) {
        dialog_content.setText("")
        dialog_title.setText("")
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