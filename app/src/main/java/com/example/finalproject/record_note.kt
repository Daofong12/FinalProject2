package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class record_note : AppCompatActivity() {

    private var layoutManager:RecyclerView.LayoutManager? = null
//    private var adapter_r : RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>? = null
    private var adapter_r: RecycleViewAdapter? = null
    private lateinit var sqliteHelper: SQLiteHelper
    private var note: NoteModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_note)

        layoutManager = LinearLayoutManager(this)
        val recycleView:RecyclerView = findViewById(R.id.recycleView)
        recycleView.layoutManager = layoutManager

        adapter_r = RecycleViewAdapter()
        recycleView.adapter = adapter_r

        sqliteHelper = SQLiteHelper(this)

        getNotes()
        adapter_r?.setOnClickItem {
            val intent = Intent(this,activity_update_note::class.java).apply{}
            intent.putExtra("id",it.id)
            intent.putExtra("date",it.date)
            intent.putExtra("title",it.subject)
            intent.putExtra("content",it.content)
            startActivity(intent)
        }

        adapter_r?.setOnclickDeleteItem {
            deleteNote(it.id)
        }

        val fab_note: View = findViewById(R.id.FAB_note)
        fab_note.setOnClickListener {
            val intent = Intent(this,activity_note_dialog::class.java).apply{}
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        getNotes()
    }

    private fun deleteNote(id: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to delete note?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") { dialog, _ ->
            sqliteHelper.deleteNoteById(id)
            getNotes()
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }

    private fun getNotes() {
        val noteList = sqliteHelper.getAllNotes()
        adapter_r?.addItems(noteList)
    }
}
