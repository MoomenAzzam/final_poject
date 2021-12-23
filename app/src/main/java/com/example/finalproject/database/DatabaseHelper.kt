package com.example.finalproject.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.finalproject.model.Book
import com.example.finalproject.model.Borrower
import com.example.finalproject.model.Favorite
import com.example.finalproject.model.User
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "LibraryDB"
        const val DATABASE_VERSION = 8
    }

    private var db: SQLiteDatabase = this.writableDatabase

    override fun onCreate(p0: SQLiteDatabase?) {
        p0!!.execSQL(Book.TABLE_CREATE)
        p0.execSQL(User.TABLE_CREATE)
        p0.execSQL(Favorite.TABLE_CREATE)
        p0.execSQL(Borrower.TABLE_CREATE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS ${Book.TABLE_NAME}")
        p0.execSQL("DROP TABLE IF EXISTS ${User.TABLE_NAME}")
        p0.execSQL("DROP TABLE IF EXISTS ${Favorite.TABLE_NAME}")
        p0.execSQL("DROP TABLE IF EXISTS ${Borrower.TABLE_NAME}")
        onCreate(p0)
    }

    //---------------------------------------------------------------------
    //Book table

    fun insertBook(
        name: String, category: String, author: String, language: String,
        numberOfPages: Int, shelfNumber: String, numberOfCopies: Int, releaseYear:Int,
        description: String, image: Bitmap
    ): Boolean {
        val cv = ContentValues()
        cv.put(Book.COL_NAME, name)
        cv.put(Book.COL_CATEGORY, category)
        cv.put(Book.COL_AUTHOR, author)
        cv.put(Book.COL_LANGUAGE, language)
        cv.put(Book.COL_NUMBER_OF_PAGES, numberOfPages)
        cv.put(Book.COL_SHELF_NUMBER, shelfNumber)
        cv.put(Book.COL_NUMBER_OF_COPIES, numberOfCopies)
        cv.put(Book.COL_RELEASE_YEAR, releaseYear)
        cv.put(Book.COL_DESCRIPTION, description)

        val byteArrayOutputStream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 30, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        cv.put(Book.COL_IMAGE, byteArray)
        return db.insert(Book.TABLE_NAME, null, cv) > 0
    }

    fun getAllBooks(): ArrayList<Book> {
        var books = ArrayList<Book>()
        val c =
            db.rawQuery("select * from ${Book.TABLE_NAME} order by ${Book.COL_ID} desc", null)
        c.moveToFirst()
        while (!c.isAfterLast) {
            val byteArray = c.getBlob(10)
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            val s = Book(
                c.getInt(0), c.getString(1),
                c.getString(2), c.getString(3),
                c.getString(4), c.getInt(5),
                c.getString(6), c.getInt(7),c.getInt(8),
                c.getString(9),
                bitmap
            )
            books.add(s)
            c.moveToNext()
        }
        c.close()
        return books
    }

    fun deleteBook(id: Int): Boolean {
        return db.delete(Book.TABLE_NAME, "${Book.COL_ID} = $id", null) > 0
    }

    fun updateBook(
        oldId: Int,
        name: String,
        author: String,
        description: String,
        category: String,
        image: Bitmap
    ): Boolean {
        val cv = ContentValues()
        cv.put(Book.COL_NAME, name)
        cv.put(Book.COL_AUTHOR, author)
        cv.put(Book.COL_DESCRIPTION, description)
        cv.put(Book.COL_CATEGORY, category)

        val byteArrayOutputStream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 30, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        cv.put(Book.COL_IMAGE, byteArray)

        return db.update(Book.TABLE_NAME, cv, "${Book.COL_ID} == $oldId", null) > 0
    }


    //---------------------------------------------------------------------
    //User table

    fun insertUser(name: String, email: String, password: String,dob: String, image: Bitmap): Boolean {
        val cv = ContentValues()
        cv.put(User.COL_NAME, name)
        cv.put(User.COL_EMAIL, email)
        cv.put(User.COL_PASSWORD, password)
        cv.put(User.COL_DOB, dob)

        val byteArrayOutputStream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 30, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        cv.put(User.COL_IMAGE, byteArray)

        return db.insert(User.TABLE_NAME, null, cv) > 0
    }

    fun getUser(id: Int): User{
        val c =
            db.rawQuery("select * from ${User.TABLE_NAME} WHERE ${User.COL_ID} = $id", null)
        c.moveToFirst()

        val byteArray = c.getBlob(5)
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)

        val user = User(c.getInt(0), c.getString(1),
            c.getString(2), c.getString(3),
            c.getString(4), bitmap)
        c.close()
        return user
    }

    fun getAllUsers(): ArrayList<User> {
        val users = ArrayList<User>()
        val c =
            db.rawQuery("select * from ${User.TABLE_NAME} order by ${User.COL_ID} desc", null)
        c.moveToFirst()
        while (!c.isAfterLast) {
            val byteArray = c.getBlob(5)
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)

            val s = User(c.getInt(0), c.getString(1),
                    c.getString(2), c.getString(3),
                c.getString(4), bitmap)
            users.add(s)
            c.moveToNext()
        }
        c.close()
        return users
    }

    fun deleteUser(id: Int): Boolean {
        return db.delete(User.TABLE_NAME, "${User.COL_ID} = $id", null) > 0
    }

    fun updateUser(
        oldId: Int,
        name: String,
        email: String,
        password: String,
        dob: String,
        image: Bitmap
    ): Boolean {
        val cv = ContentValues()
        cv.put(User.COL_NAME, name)
        cv.put(User.COL_EMAIL, email)
        cv.put(User.COL_PASSWORD, password)
        cv.put(User.COL_DOB, dob)

        val byteArrayOutputStream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 30, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        cv.put(User.COL_IMAGE, byteArray)

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
            db.rawQuery(
                "select * from ${Favorite.TABLE_NAME} INNER JOIN ${Book.TABLE_NAME} ON (${Favorite.TABLE_NAME}.${Favorite.COL_BOOK_ID} = ${Book.TABLE_NAME}.${Book.COL_ID}) WHERE ${Favorite.COL_USER_ID} = $userId order by ${Favorite.COL_BOOK_ID} desc",
                null
            )
        c.moveToFirst()
        while (!c.isAfterLast) {
            val byteArray = c.getBlob(10)
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            val s = Book(
                c.getInt(0), c.getString(1),
                c.getString(2), c.getString(3),
                c.getString(4), c.getInt(5),
                c.getString(6), c.getInt(7),c.getInt(8),
                c.getString(9),
                bitmap
            )
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
                "select * from ${Favorite.TABLE_NAME} WHERE ${Favorite.COL_USER_ID} = $userId AND ${Favorite.COL_BOOK_ID} = $bookId",
                null
            )
        return c.count > 0
    }

    fun deleteFavorite(userId: Int, bookId: Int): Boolean {
        return db.delete(
            Favorite.TABLE_NAME,
            "${Favorite.COL_USER_ID} = $userId AND ${Favorite.COL_BOOK_ID} = $bookId",
            null
        ) > 0
    }


    //---------------------------------------------------------------------
    //borrower table


    fun insertBorrower(bookId: Int, name: String): Boolean {
        val cv = ContentValues()
        cv.put(Borrower.COL_BOOK_ID, bookId)
        cv.put(Borrower.COL_NAME, name)

        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        val date = "$year:${month+1}:$day"

        cv.put(Borrower.COL_BORROW_DATE, date)
        return db.insert(Borrower.TABLE_NAME, null, cv) > 0
    }

    fun getAllBorrowersForBook(bookId: Int): ArrayList<Borrower> {
        var borrowers = ArrayList<Borrower>()
        val c =
            db.rawQuery("select * from ${Borrower.TABLE_NAME} WHERE ${Borrower.COL_BOOK_ID} = $bookId" +
                    " order by ${Borrower.COL_ID} desc", null)
        c.moveToFirst()
        while (!c.isAfterLast) {
            val s = Borrower(
                c.getInt(0), c.getInt(1),c.getString(2),
                c.getString(3))
            borrowers.add(s)
            c.moveToNext()
        }
        c.close()
        return borrowers
    }

    fun deleteBorrower(id: Int): Boolean {
        return db.delete(Borrower.TABLE_NAME, "${Borrower.COL_ID} = $id", null) > 0
    }

}

