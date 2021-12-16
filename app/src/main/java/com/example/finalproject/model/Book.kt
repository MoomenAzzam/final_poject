package com.example.finalproject.model

data class Book(val id:Int, val name:String, val author:String, val description:String, val image:String){
    companion object{
        const val TABLE_NAME = "Book"
        const val COL_ID = "id"
        const val COL_NAME = "name"
        const val COL_AUTHOR = "author"
        const val COL_DESCRIPTION = "description"
        const val COL_IMAGE = "image"
        const val TABLE_CREATE =
            "create table $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COL_NAME TEXT NOT NULL, $COL_AUTHOR TEXT NOT NULL, $COL_DESCRIPTION TEXT NOT NULL, $COL_IMAGE TEXT)"
    }
}