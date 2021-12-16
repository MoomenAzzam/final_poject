package com.example.finalproject.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.finalproject.model.Book
import com.example.finalproject.model.Favorite
import com.example.finalproject.model.User

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    private var db: SQLiteDatabase = this.writableDatabase

    override fun onCreate(p0: SQLiteDatabase?) {
        p0!!.execSQL(Book.TABLE_CREATE)
        p0!!.execSQL(User.TABLE_CREATE)
        p0!!.execSQL(Favorite.TABLE_CREATE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS ${Book.TABLE_NAME}")
        p0!!.execSQL("DROP TABLE IF EXISTS ${User.TABLE_NAME}")
        p0!!.execSQL("DROP TABLE IF EXISTS ${Favorite.TABLE_NAME}")
        onCreate(p0)
    }

    //---------------------------------------------------------------------
    //Book table

    fun insertBook(name: String, author: String, description: String, image:String): Boolean {
        val cv = ContentValues()
        cv.put(Book.COL_NAME, name)
        cv.put(Book.COL_AUTHOR, author)
        cv.put(Book.COL_DESCRIPTION, description)
        cv.put(Book.COL_IMAGE, image)
        return db.insert(Book.TABLE_NAME, null, cv) > 0
    }

    fun getAllBooks(): ArrayList<Book> {
        var books = ArrayList<Book>()
        val c =
            db.rawQuery("select * from ${Book.TABLE_NAME} order by ${Book.COL_ID} desc", null)
        c.moveToFirst()
        while (!c.isAfterLast) {
            val s = Book(c.getInt(0), c.getString(1), c.getString(2),c.getString(3), c.getString(4))
            books.add(s)
            c.moveToNext()
        }
        c.close()
        return books
    }

    fun deleteBook(id: Int): Boolean {
        return db.delete(Book.TABLE_NAME, "${Book.COL_ID} = $id", null) > 0
    }

    fun updateBook(oldId: Int, name: String, author: String, description: String, image:String): Boolean {
        val cv = ContentValues()
        cv.put(Book.COL_NAME, name)
        cv.put(Book.COL_AUTHOR, author)
        cv.put(Book.COL_DESCRIPTION, description)
        cv.put(Book.COL_IMAGE, image)
        return db.update(Book.TABLE_NAME, cv, "${Book.COL_ID} == $oldId", null) > 0
    }


    //---------------------------------------------------------------------
    //User table

    fun insertUser(name: String, email: String, password: String, image:String): Boolean {
        val cv = ContentValues()
        cv.put(User.COL_NAME, name)
        cv.put(User.COL_EMAIL, email)
        cv.put(User.COL_PASSWORD, password)
        cv.put(User.COL_IMAGE, image)
        return db.insert(User .TABLE_NAME, null, cv) > 0
    }

    fun getAllUsers(): ArrayList<User> {
        var users = ArrayList<User>()
        val c =
            db.rawQuery("select * from ${User.TABLE_NAME} order by ${User.COL_ID} desc", null)
        c.moveToFirst()
        while (!c.isAfterLast) {
            val s = User(c.getInt(0), c.getString(1), c.getString(2),c.getString(3), c.getString(4))
            users.add(s)
            c.moveToNext()
        }
        c.close()
        return users
    }

    fun deleteUser(id: Int): Boolean {
        return db.delete(User.TABLE_NAME, "${User.COL_ID} = $id", null) > 0
    }

    fun updateUser(oldId: Int, name: String, email: String, password: String, image:String): Boolean {
        val cv = ContentValues()
        cv.put(User.COL_NAME, name)
        cv.put(User.COL_EMAIL, email)
        cv.put(User.COL_PASSWORD, password)
        cv.put(User.COL_IMAGE, image)
        return db.update(User.TABLE_NAME, cv, "${User.COL_ID} == $oldId", null) > 0
    }


    //---------------------------------------------------------------------
    //Favorite table

    fun insertFavorite(userId: Int, bookId: Int): Boolean {
        val cv = ContentValues()
        cv.put(Favorite.COL_USER_ID, userId)
        cv.put(Favorite.COL_BOOK_ID, bookId)
        return db.insert(Favorite.TABLE_NAME, null, cv) > 0
    }

    fun getAllFavoritesForUser(userId: Int): ArrayList<Book> {
        var favorites = ArrayList<Book>()
        val c =
            db.rawQuery("select * from ${Favorite.TABLE_NAME} INNER JOIN ${Book.TABLE_NAME} ON (${Favorite.TABLE_NAME}.${Favorite.COL_BOOK_ID} = ${Book.TABLE_NAME}.${Book.COL_ID}) WHERE ${Favorite.COL_USER_ID} = $userId order by ${Favorite.COL_BOOK_ID} desc", null)
        c.moveToFirst()
        while (!c.isAfterLast) {
            val s = Book(c.getInt(0), c.getString(1), c.getString(2),c.getString(3), c.getString(4))
            favorites.add(s)
            c.moveToNext()
        }
        c.close()
        return favorites
    }

    fun isFavorite(userId: Int, bookId: Int): Boolean {
        var favorites = ArrayList<Favorite>()
        val c =
            db.rawQuery(
                "select * from ${Favorite.TABLE_NAME} WHERE ${Favorite.COL_USER_ID} = $userId AND ${Favorite.COL_BOOK_ID} = $bookId", null)
        return c.count > 0
    }

        fun deleteFavorite(userId: Int, bookId: Int): Boolean {
        return db.delete(Favorite.TABLE_NAME, "${Favorite.COL_USER_ID} = $userId AND ${Favorite.COL_BOOK_ID} = $bookId", null) > 0
    }



    companion object {
        const val DATABASE_NAME = "LibraryDB"
        const val DATABASE_VERSION = 1
    }
}

