package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class record_note : AppCompatActivity() {

    private var layoutManager:RecyclerView.LayoutManager? = null
    private var adapter_r : RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_note)

        layoutManager = LinearLayoutManager(this)
        val recycleView:RecyclerView = findViewById(R.id.recycleView)
        recycleView.layoutManager = layoutManager

        adapter_r = RecycleViewAdapter()
        recycleView.adapter = adapter_r

        val fab_note: View = findViewById(R.id.FAB_note)
        fab_note.setOnClickListener {
            val intent = Intent(this,activity_note_dialog::class.java).apply{}
            startActivity(intent)
        }
    }
}
