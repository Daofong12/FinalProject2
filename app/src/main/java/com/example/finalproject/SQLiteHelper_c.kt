package com.example.finalproject

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.drm.DrmStore.DrmObjectType.CONTENT
import android.os.Build.ID
import android.util.Log

class SQLiteHelper_c(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "checklist.db"
        private const val DATABASE_VERSION = 1
        private const val TBL_CHECKLIST = "tbl_checklist"
        private const val ID_c = "id_c"
        private const val DATE_c = "date_c"
        private const val TIME = "time"
        private const val CATEGORY = "category"
        private const val EVENT = "event"
        private const val LOCATION = "location"
        private const val ISSELECTED = "isSelected"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        //TODO:create table
        val createTblChecklist = ("CREATE TABLE " + TBL_CHECKLIST + "("
                + ID_c + " INTEGER PRIMARY KEY," + DATE_c + " TEXT,"
                + TIME + " TEXT," + CATEGORY + " TEXT," + EVENT + " TEXT,"
                + LOCATION + " TEXT," + ISSELECTED + " INTEGER"+")")
        db?.execSQL(createTblChecklist)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_CHECKLIST")
        onCreate(db)
    }

    fun insertChecklist(checklist: ChecklistModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID_c, checklist.id_c)
        contentValues.put(DATE_c, checklist.date_c)
        contentValues.put(TIME, checklist.time)
        contentValues.put(CATEGORY, checklist.category)
        contentValues.put(EVENT, checklist.event)
        contentValues.put(LOCATION, checklist.location)
        contentValues.put(ISSELECTED, checklist.isSelected)

        val success = db.insert(TBL_CHECKLIST, null, contentValues)
        //db.close()
        return success
    }

    @SuppressLint("Range")
    fun getAllChecklist(): ArrayList<ChecklistModel> {
        val checklistList: ArrayList<ChecklistModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_CHECKLIST"
        val db = this.readableDatabase

        val cursor: Cursor?

        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id_c: Int
        var date_c: String
        var time: String
        var category: String
        var event: String
        var location: String
        var isSelected:Int

        if (cursor.moveToFirst()) {
            do {
                id_c = cursor.getInt(cursor.getColumnIndex("id_c"))
                date_c = cursor.getString(cursor.getColumnIndex("date_c"))
                time = cursor.getString(cursor.getColumnIndex("time"))
                category = cursor.getString(cursor.getColumnIndex("category"))
                event = cursor.getString(cursor.getColumnIndex("event"))
                location = cursor.getString(cursor.getColumnIndex("location"))
                isSelected = cursor.getInt(cursor.getColumnIndex("isSelected"))

                val checklist = ChecklistModel (id_c = id_c, date_c = date_c, time = time, category = category
                    , event = event, location = location, isSelected = isSelected)
                checklistList.add(checklist)
            }while (cursor.moveToNext())
        }

        return checklistList
    }

    fun updateChecklist(checklist: ChecklistModel): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID_c, checklist.id_c)
        contentValues.put(DATE_c, checklist.date_c)
        contentValues.put(TIME, checklist.time)
        contentValues.put(CATEGORY, checklist.category)
        contentValues.put(EVENT, checklist.event)
        contentValues.put(LOCATION, checklist.location)
        contentValues.put(ISSELECTED, checklist.isSelected)

        val success = db.update(TBL_CHECKLIST, contentValues, "id_c =" + checklist.id_c, null)
        db.close()
        return success
    }

    fun deleteChecklistById(id: Int): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID_c, id)

        val success = db.delete(TBL_CHECKLIST, "id_c = $id", null)
        db.close()
        return success
    }
}