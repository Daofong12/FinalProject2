package com.example.finalproject

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "record.db"
        private const val DATABASE_VERSION = 1
        private const val TBL_NOTE = "tbl_note"
        private const val ID = "id"
        private const val DATE = "date"
        private const val SUBJECT = "subject"
        private const val CONTENT = "content"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblnote = ("CREATE TABLE " + TBL_NOTE + "("
                + ID + " INTEGER PRIMARY KEY," + DATE + " TEXT,"
                + SUBJECT + " TEXT," + CONTENT + " TEXT" + ")")
        db?.execSQL(createTblnote)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_NOTE")
        onCreate(db)
    }


    fun insertNote(note: NoteModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, note.id)
        contentValues.put(DATE, note.date)
        contentValues.put(SUBJECT, note.subject)
        contentValues.put(CONTENT, note.content)

        val success = db.insert(TBL_NOTE, null, contentValues)
        db.close()
        return success
    }

    @SuppressLint("Range")
    fun getAllNotes(): ArrayList<NoteModel> {
        val noteList: ArrayList<NoteModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_NOTE"
        val db = this.readableDatabase

        val cursor: Cursor?

        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var date: String
        var subject: String
        var content: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                date = cursor.getString(cursor.getColumnIndex("date"))
                subject = cursor.getString(cursor.getColumnIndex("subject"))
                content = cursor.getString(cursor.getColumnIndex("content"))

                val note = NoteModel(id = id, date = date, subject = subject, content = content)
                noteList.add(note)
            }while (cursor.moveToNext())
        }

        return noteList
    }

    fun updateNote(note: NoteModel): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, note.id)
        contentValues.put(DATE, note.date)
        contentValues.put(SUBJECT, note.subject)
        contentValues.put(CONTENT, note.content)

        val success = db.update(TBL_NOTE, contentValues, "id=" + note.id, null)
        db.close()
        return success
    }

    fun deleteNoteById(id: Int): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, id)

        val success = db.delete(TBL_NOTE, "id=$id", null)
        db.close()
        return success
    }
}