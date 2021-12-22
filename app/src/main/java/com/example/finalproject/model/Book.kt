package com.example.finalproject.model

import android.graphics.Bitmap

data class Book(val id:Int, val name:String, val category:String, val author:String,
                val language:String, val numberOfPages:Int, val shelfNumber:String,
                val numberOfCopies:Int,val releaseYear:Int, val description:String,
                val image:Bitmap){
    companion object{
        const val TABLE_NAME = "Book"
        const val COL_ID = "id"
        const val COL_NAME = "name"
        const val COL_CATEGORY = "category"
        const val COL_AUTHOR = "author"
        const val COL_LANGUAGE = "language"
        const val COL_NUMBER_OF_PAGES = "numberOfPages"
        const val COL_SHELF_NUMBER = "shelfNumber"
        const val COL_NUMBER_OF_COPIES = "numberOfCopies"
        const val COL_RELEASE_YEAR = "releaseYear"
        const val COL_DESCRIPTION = "description"
        const val COL_IMAGE = "image"
        const val TABLE_CREATE =
            "create table $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COL_NAME TEXT NOT NULL, $COL_CATEGORY TEXT NOT NULL," +
                    "$COL_AUTHOR TEXT NOT NULL, $COL_LANGUAGE TEXT NOT NULL," +
                    "$COL_NUMBER_OF_PAGES INTEGER NOT NULL, $COL_SHELF_NUMBER TEXT NOT NULL," +
                    "$COL_NUMBER_OF_COPIES INTEGER NOT NULL, " +
                    "$COL_RELEASE_YEAR INTEGER NOT NULL, $COL_DESCRIPTION TEXT NOT NULL," +
                    "$COL_IMAGE BLOB)"
    }
}