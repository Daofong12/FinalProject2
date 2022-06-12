package com.example.finalproject

import android.view.LayoutInflater
import android.view.ScrollCaptureCallback
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecycleViewAdapterOfChecklist:RecyclerView.Adapter<RecycleViewAdapterOfChecklist.ViewHolder>(){

//    private val itemDate = arrayOf("test1","test2","test3","test4","test5","test6")
//    private val itemTitle = arrayOf("test11","test22","test33","test44","test55","test66")
//    private val itemcotent = arrayOf("test10","test20","test30","test40","test50","test60")
    private var checklistList: ArrayList<ChecklistModel> = ArrayList()
    private var onClickItem: ((ChecklistModel) -> Unit)? = null
    private var onClickDeleteItem: ((ChecklistModel) -> Unit)? = null


    fun addItems(items: ArrayList<ChecklistModel>) {
        this.checklistList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (ChecklistModel) -> Unit) {
        this.onClickItem = callback
    }

    fun setOnclickDeleteItem(callback: (ChecklistModel) -> Unit) {
        this.onClickDeleteItem = callback
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var date : TextView
        var time : TextView
        var category : TextView
        var event : TextView
        var location : TextView
        var btnDelete : Button

        init {
            date = itemView.findViewById(R.id.checklist_date)
            time = itemView.findViewById(R.id.checklist_time)
            category = itemView.findViewById(R.id.checklist_category)
            event = itemView.findViewById(R.id.checklist_event)
            location = itemView.findViewById(R.id.checklist_location)
            btnDelete = itemView.findViewById(R.id.delete_btn_c)
        }

        fun bindView(checklist: ChecklistModel) {
            date.text = checklist.date_c
            time.text = checklist.time
            category.text = checklist.category
            event.text = checklist.event
            location.text = checklist.location
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycleview_model_checklist,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.date.text = itemDate[position]
//        holder.textTitle.text = itemTitle[position]
//        holder.textContent.text = itemcotent[position]
        val checklist = checklistList[position]
        holder.bindView(checklist)
        holder.itemView.setOnClickListener{ onClickItem?.invoke(checklist)}
        holder.btnDelete.setOnClickListener{ onClickDeleteItem?.invoke(checklist)}
    }

    override fun getItemCount(): Int {
        return checklistList.size
    }
}