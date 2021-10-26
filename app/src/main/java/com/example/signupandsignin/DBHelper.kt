package com.example.signupandsignin

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

//details.dp is the database name
class DBHelper(
    context: Context
) : SQLiteOpenHelper(context, "user.dp", null, 3) {
    var sqLightDatabase: SQLiteDatabase = writableDatabase
    override fun onCreate(dp: SQLiteDatabase?) {
        if (dp != null) {
            dp.execSQL("create table User ( Name text,Phone text,Location text,Username text, Password text)")
        }

    }

    override fun onUpgrade(dp: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion && dp != null) {
            dp.execSQL("DROP TABLE IF EXISTS User")
            onCreate(dp)
        }
    }

    fun saveData(userObj: User): Long {
        val cv = ContentValues()
        cv.put("Name", userObj.name)
        cv.put("Phone", userObj.phone)
        cv.put("Location", userObj.location)
        cv.put("Username", userObj.username)
        cv.put("Password", userObj.password)

        var status = sqLightDatabase.insert("User", null, cv)//status
        return status

    }

    @SuppressLint("Range")
    fun readData() :ArrayList<User>{
        var selectQuery = "SELECT  * FROM User"
        var cursor: Cursor? = null
        try {

            cursor = sqLightDatabase.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            sqLightDatabase.execSQL(selectQuery)
        }
        var users=ArrayList<User>()
        var name: String
        var phone: String
        var location: String
        var username: String
        var password: String
        if (cursor != null && cursor.getCount()>0) {
            if (cursor.moveToFirst()) {
                do {
                    name = cursor.getString(cursor.getColumnIndex("Name"))
                    phone = cursor.getString(cursor.getColumnIndex("Phone"))
                    location = cursor.getString(cursor.getColumnIndex("Location"))
                    username = cursor.getString(cursor.getColumnIndex("Username"))
                    password = cursor.getString(cursor.getColumnIndex("Password"))

                    users.add(User(name,phone,location,username,password))

                } while (cursor.moveToNext())
            }
        }
        return users
    }

}