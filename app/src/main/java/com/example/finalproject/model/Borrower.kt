package com.example.finalproject.model

data class Borrower(val id: Int, val name: String, val borrowDate: String) {

    companion object {
        const val TABLE_NAME = "Borrower"
        const val COL_ID = "id"
        const val COL_NAME = "name"
        const val COL_BORROW_DATE = "borrowDate"
        const val TABLE_CREATE =
            "create table $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COL_NAME TEXT NOT NULL, $COL_BORROW_DATE TEXT NOT NULL)"
    }
}