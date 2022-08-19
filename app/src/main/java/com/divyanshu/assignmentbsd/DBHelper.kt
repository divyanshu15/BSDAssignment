package com.divyanshu.assignmentbsd

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.location.Address

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    companion object{
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "AllLocation.db"

        // below is the variable for database version
        private val DATABASE_VERSION = 3

        // below is the variable for table name
        val TABLE_NAME = "Locations"


        // below is the variable for Longitude column
        val Longitude_col = "longitude"
        val ID_col = "ID"

        // below is the variable for Latitude column
        val Latitude_col = "latitude"
        //val time_col = "time"

        // below is the variable for Address column
        val Address_COL = "address"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_col + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Longitude_col + " Text, " +
                Latitude_col + " TEXT," +
                Address_COL + " TEXT" + ")")

        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)

        onCreate(db)
    }

    fun addLocation(locModel: locModel ){

        val values = ContentValues()
        values.put(Longitude_col,locModel.longitude)
        values.put(Latitude_col, locModel.latitude)
        //values.put(time_col, locModel.time)
        values.put(Address_COL, locModel.address)

        val db = this.writableDatabase

        // all values are inserted into database
        db.insert(TABLE_NAME, null, values)

        // at last we are
        // closing our database
        db.close()
    }
    fun getLocation(): Cursor? {

        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)

    }

}