package com.example.finalproject

import android.view.LayoutInflater
import android.view.ScrollCaptureCallback
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecycleViewAdapter:RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>(){

//    private val itemDate = arrayOf("test1","test2","test3","test4","test5","test6")
//    private val itemTitle = arrayOf("test11","test22","test33","test44","test55","test66")
//    private val itemcotent = arrayOf("test10","test20","test30","test40","test50","test60")
    private var noteList: ArrayList<NoteModel> = ArrayList()
    private var onClickItem: ((NoteModel) -> Unit)? = null
    private var onClickDeleteItem: ((NoteModel) -> Unit)? = null


    fun addItems(items: ArrayList<NoteModel>) {
        this.noteList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (NoteModel) -> Unit) {
        this.onClickItem = callback
    }

    fun setOnclickDeleteItem(callback: (NoteModel) -> Unit) {
        this.onClickDeleteItem = callback
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var date : TextView
        var textTitle : TextView
        var textContent : TextView
        var btnDelete : Button

        init {
            date = itemView.findViewById(R.id.note_date)
            textTitle = itemView.findViewById(R.id.note_title)
            textContent = itemView.findViewById(R.id.note_cotent)
            btnDelete = itemView.findViewById(R.id.delete_btn)
        }

        fun bindView(note: NoteModel) {
            date.text = note.date
            textTitle.text = note.subject
            textContent.text = note.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycleview_model,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.date.text = itemDate[position]
//        holder.textTitle.text = itemTitle[position]
//        holder.textContent.text = itemcotent[position]
        val note = noteList[position]
        holder.bindView(note)
        holder.itemView.setOnClickListener{ onClickItem?.invoke(note)}
        holder.btnDelete.setOnClickListener{ onClickDeleteItem?.invoke(note)}
    }

    override fun getItemCount(): Int {
        return noteList.size
    }
}