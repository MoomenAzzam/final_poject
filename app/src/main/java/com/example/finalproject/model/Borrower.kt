package com.example.finalproject.model

data class Borrower(val id: Int, val bookId:Int, val name: String, val borrowDate: String) {

    companion object {
        const val TABLE_NAME = "Borrower"
        const val COL_ID = "id"
        const val COL_BOOK_ID = "bookId"
        const val COL_NAME = "name"
        const val COL_BORROW_DATE = "borrowDate"
        const val TABLE_CREATE =
            "create table $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COL_BOOK_ID INTEGER, $COL_NAME TEXT NOT NULL, $COL_BORROW_DATE TEXT NOT NULL," +
                    "FOREIGN KEY (${Borrower.COL_BOOK_ID}) REFERENCES ${Book.TABLE_NAME}(${Book.COL_ID}))"
    }
}