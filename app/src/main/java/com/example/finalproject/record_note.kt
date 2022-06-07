package com.example.finalproject

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.text.Editable
import android.util.AttributeSet
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
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.collections.ArrayList

class record_note : AppCompatActivity() {
    private val RQ_SPEECH_REC = 102
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_note)

        val fab_note: View = findViewById(R.id.FAB_note)
        fab_note.setOnClickListener{
            val mydialogView = LayoutInflater.from(this).inflate(R.layout.note_dialog,null)
            val mydialogBuilder = AlertDialog.Builder(this)
                .setView(mydialogView)
            val myAlertDialog = mydialogBuilder.show()

            val fab_cancel:View = mydialogView.findViewById(R.id.note_dialog_cancel_fab)
            val btn_save:Button = mydialogView.findViewById(R.id.button_save)

            val microphone_btn:Button = mydialogView.findViewById(R.id.microphone_btn)

            //TODO:日期取得、麥克風

            microphone_btn.setOnClickListener {
                askSpeechInput()
            }



            btn_save.setOnClickListener{
                //關閉彈出視窗
                myAlertDialog.dismiss()
                Toast.makeText(this,"儲存成功", Toast.LENGTH_SHORT).show()
                //TODO:資料取得

            }
            fab_cancel.setOnClickListener{
                myAlertDialog.dismiss()
            }
        }
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result: ActivityResult? ->
            if (result!!.resultCode == RESULT_OK && result!!.data != null) {
                val speechtext = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<Editable>

                val mydialogView = LayoutInflater.from(this).inflate(R.layout.note_dialog,null)
                val dialog_content:EditText = mydialogView.findViewById(R.id.dialog_content)
                val speech_recog_text:TextView = mydialogView.findViewById(R.id.speechRecogText)


                val speechstring = speechtext[0].toString()
                Log.v("test", speechstring)
                fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
                dialog_content.text = speechstring.toEditable()
                speech_recog_text.text = speechstring
            }
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
            } catch (exp:ActivityNotFoundException) {
                Toast.makeText(applicationContext, "Device does not suppose", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
