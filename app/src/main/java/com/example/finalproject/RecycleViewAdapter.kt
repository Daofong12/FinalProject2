package com.example.finalproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecycleViewAdapter:RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>(){

    private val itemDate = arrayOf("test1","test2","test3","test4","test5","test6")
    private val itemTitle = arrayOf("test11","test22","test33","test44","test55","test66")
    private val itemcotent = arrayOf("test10","test20","test30","test40","test50","test60")

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var date : TextView
        var textTitle : TextView
        var textContent : TextView

        init {
            date = itemView.findViewById(R.id.note_date)
            textTitle = itemView.findViewById(R.id.note_title)
            textContent = itemView.findViewById(R.id.note_cotent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycleview_model,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.date.text = itemDate[position]
        holder.textTitle.text = itemTitle[position]
        holder.textContent.text = itemcotent[position]
    }

    override fun getItemCount(): Int {
        return itemTitle.size
    }
}