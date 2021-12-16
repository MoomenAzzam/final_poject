package com.example.finalproject.model

data class Favorite(val userId:Int, val bookId: Int){
    companion object{
        const val TABLE_NAME = "Favorite"
        const val COL_USER_ID = "userId"
        const val COL_BOOK_ID = "bookId"
        const val TABLE_CREATE =
            "create table $TABLE_NAME ($COL_USER_ID INTEGER , $COL_BOOK_ID INTEGER, " +
                    "PRIMARY KEY($COL_USER_ID, $COL_BOOK_ID), " +
                    "FOREIGN KEY ($COL_USER_ID) REFERENCES ${User.TABLE_NAME}, " +
                    "FOREIGN KEY ($COL_BOOK_ID) REFERENCES ${Book.TABLE_NAME}"
    }
}