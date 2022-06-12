package com.example.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class activity_update_note : AppCompatActivity() {
    private lateinit var sqliteHelper: SQLiteHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_note)

        val date = intent.getStringExtra("date")
        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val id = intent.getIntExtra("id", 0)

        val date_text:TextView = findViewById(R.id.dialog_date_update)
        val title_text:EditText = findViewById(R.id.dialog_title_update)
        val content_text:EditText = findViewById(R.id.dialog_content_update)
        val update_btn: Button = findViewById(R.id.button_update)

        sqliteHelper = SQLiteHelper(this)


        date_text.setText(date)
        title_text.setText(title)
        content_text.setText(content)

        update_btn.setOnClickListener {
            val new_title = title_text.text.toString()
            val new_content = content_text.text.toString()

            if(new_title == title && new_content == content) {
                Toast.makeText(this, "Not changed...", Toast.LENGTH_SHORT).show()
            }

            val note = NoteModel(id = id, date = date.toString(), subject = new_title, content = new_content)
            val status = sqliteHelper.updateNote(note)
            if (status > -1) {
                clearEditText(title_text, content_text)
            } else {
                Toast.makeText(this, "Update failed...", Toast.LENGTH_SHORT).show()
            }

            finish()
        }
    }

    private fun clearEditText(dialog_title:EditText, dialog_content:EditText) {
        dialog_content.setText("")
        dialog_title.setText("")
    }
}