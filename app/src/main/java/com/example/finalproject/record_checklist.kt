package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class record_checklist : AppCompatActivity() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter_c: RecycleViewAdapterOfChecklist? = null
    private lateinit var sqliteHelper_c: SQLiteHelper_c
    private var checklist: ChecklistModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_checklist)

        val date_text:TextView = findViewById(R.id.textView_checklist_date)
        date_text.setText(getNow())

        layoutManager = LinearLayoutManager(this)
        val recycleView:RecyclerView = findViewById(R.id.recycleView_checklist)
        recycleView.layoutManager = layoutManager

        adapter_c = RecycleViewAdapterOfChecklist()
        recycleView.adapter = adapter_c

        sqliteHelper_c = SQLiteHelper_c(this)
        getChecklist()

        adapter_c?.setOnClickItem {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("確認此項活動:${it.event} 已完成?")
            builder.setMessage("種類:${it.category} \n地點:${it.location} \n" +
                    "時間:${it.date_c} ${it.time}")
            builder.setCancelable(true)
            builder.setPositiveButton("Yes") { dialog, _ ->
                val checklist = ChecklistModel(id_c = it.id_c,date_c = it.date_c, time = it.time, category = it.category,
                    event = it.event, location = it.location, isSelected = 1)
                sqliteHelper_c.updateChecklist(checklist)
                getChecklist()
                dialog.dismiss()
            }
            builder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }

            val alert = builder.create()
            alert.show()
        }

        //TODO:X:update;O:checked/unchecked
        adapter_c?.setOnclickEditItem {
            val intent = Intent(this,activity_update_checklist::class.java).apply{}
            intent.putExtra("id_c",it.id_c)
            intent.putExtra("date_c",it.date_c)
            intent.putExtra("time",it.time)
            intent.putExtra("category",it.category)
            intent.putExtra("event",it.event)
            intent.putExtra("location",it.location)
            intent.putExtra("isSelected",it.isSelected)
            startActivity(intent)
        }

        adapter_c?.setOnclickDeleteItem {
            deleteChecklist(it.id_c)
        }

        val fab_note: View = findViewById(R.id.FAB_checklist)
        fab_note.setOnClickListener {
            val intent = Intent(this,activity_checklist_dialog::class.java).apply{}
            startActivity(intent)
        }
    }
    override fun onResume() {
        super.onResume()
        getChecklist()
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
    private fun getChecklist() {
        val checklistList = sqliteHelper_c.getAllChecklist()
        adapter_c?.addItems(checklistList)
    }

    private fun deleteChecklist(id_c: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to delete checklist?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") { dialog, _ ->
            sqliteHelper_c.deleteChecklistById(id_c)
            getChecklist()
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }
}