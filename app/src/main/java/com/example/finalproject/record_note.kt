package com.example.finalproject

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar

class record_note : AppCompatActivity() {
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

            //TODO:日期取得、麥克風

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
    }

}