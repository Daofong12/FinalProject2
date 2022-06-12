package com.example.finalproject

import java.util.*

data class NoteModel (
    var id: Int = getAutoId(),
    var date: String = "",
    var subject: String = "",
    var content: String = ""
) {
    companion object{
        fun getAutoId(): Int{
            val random = Random()
            return random.nextInt(100)
        }
    }
}