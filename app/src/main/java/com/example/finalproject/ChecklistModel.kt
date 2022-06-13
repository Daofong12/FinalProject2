package com.example.finalproject

import com.example.finalproject.NoteModel.Companion.getAutoId
import java.util.*

class ChecklistModel (
    var id_c: Int = getAutoId(),
    var category: String = "",
    var event: String = "",
    var location: String = "",
    var date_c: String = "",
    var time:String = "",
    var isSelected:Int = 0

    ) {
        companion object{
            fun getAutoId(): Int{
                val random = Random()
                return random.nextInt(100)
            }
        }
    }