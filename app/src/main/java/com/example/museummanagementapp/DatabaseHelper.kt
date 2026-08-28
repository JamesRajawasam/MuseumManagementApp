package com.example.museummanagementapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "MuseumDB.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE exhibits (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, category TEXT, price TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS exhibits")
        onCreate(db)
    }

    fun insertExhibit(name: String, category: String, price: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("name", name)
            put("category", category)
            put("price", price)
        }
        val result = db.insert("exhibits", null, contentValues)
        return result != -1L
    }
}