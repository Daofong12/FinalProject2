package com.example.finalproject

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapterOfHome : RecyclerView.Adapter<RecyclerViewAdapterOfHome.ViewHolder>(){
    private val itemimage = intArrayOf(R.drawable.med,R.drawable.dia2,R.drawable.diet,R.drawable.care)
    private var homeList: ArrayList<ChecklistModel> = ArrayList()
    private var onClickItem: ((ChecklistModel) -> Unit)? = null
    private var onClickEditItem: ((ChecklistModel) -> Unit)? = null
    private var onClickDeleteItem: ((ChecklistModel) -> Unit)? = null


    fun addItems(items: ArrayList<ChecklistModel>) {
        this.homeList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (ChecklistModel) -> Unit) {
        this.onClickItem = callback
    }

    fun setOnclickDeleteItem(callback: (ChecklistModel) -> Unit) {
        this.onClickDeleteItem = callback
    }

    fun setOnclickEditItem(callback: (ChecklistModel) -> Unit) {
        this.onClickEditItem = callback
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var card : CardView
        var date : TextView
        var time : TextView
        //var category : TextView
        var img_category: ImageView
        var event : TextView
        var location : TextView

        init {
            card = itemView.findViewById(R.id.cardView_home)
            date = itemView.findViewById(R.id.home_date)
            time = itemView.findViewById(R.id.home_time)
            //category = itemView.findViewById(R.id.checklist_category)
            img_category = itemView.findViewById(R.id.imageView_category_home)
            event = itemView.findViewById(R.id.home_event)
            location = itemView.findViewById(R.id.home_location)
        }

        fun bindView(checklist: ChecklistModel) {
            date.text = checklist.date_c
            time.text = checklist.time
            //category.text = checklist.category
            if(checklist.category == "medicine"){
                img_category.setImageResource(itemimage[0])
            }
            else if(checklist.category == "diagnosis"){
                img_category.setImageResource(itemimage[1])
            }
            else if(checklist.category == "diet"){
                img_category.setImageResource(itemimage[2])
            }
            else{
                img_category.setImageResource(itemimage[3])
            }
            event.text = checklist.event
            location.text = checklist.location
            if (checklist.isSelected == 1){
                card.setCardBackgroundColor(Color.parseColor("#4FAFAA"))
            }
            else{
                card.setCardBackgroundColor(Color.parseColor("#FFB865"))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_model_home,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.date.text = itemDate[position]
//        holder.textTitle.text = itemTitle[position]
//        holder.textContent.text = itemcotent[position]
        val checklist = homeList[position]
        holder.bindView(checklist)
        holder.itemView.setOnClickListener{ onClickItem?.invoke(checklist)}
    }

    override fun getItemCount(): Int {
        return homeList.size
    }
}