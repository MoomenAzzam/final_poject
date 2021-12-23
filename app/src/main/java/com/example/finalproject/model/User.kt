package com.example.finalproject.model

import android.graphics.Bitmap

data class User(val id:Int, val name:String, val email:String, val password:String,
                val dob:String, val image:Bitmap){
    companion object{
        const val TABLE_NAME = "User"
        const val COL_ID = "id"
        const val COL_NAME = "name"
        const val COL_EMAIL = "email"
        const val COL_PASSWORD = "password"
        const val COL_DOB = "dob"
        const val COL_IMAGE = "image"
        const val TABLE_CREATE =
            "create table $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COL_NAME TEXT NOT NULL, $COL_EMAIL TEXT NOT NULL, " +
                    "$COL_PASSWORD TEXT NOT NULL, $COL_DOB TEXT NOT NULL, $COL_IMAGE BLOB)"
    }
}