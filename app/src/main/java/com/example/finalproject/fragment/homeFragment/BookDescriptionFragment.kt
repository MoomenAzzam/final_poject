package com.example.finalproject.fragment.homeFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalproject.MainActivity
import com.example.finalproject.database.DatabaseHelper
import com.example.finalproject.databinding.FragmentBookDescriptionBinding

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val BOOK_ID = "bookId"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class BookDescriptionFragment : Fragment() {

    private var bookId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            bookId = it.getInt(BOOK_ID)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentBookDescriptionBinding.inflate(inflater, container, false)
        val db = DatabaseHelper(requireContext())

        val book = db.getBook(bookId!!)
        binding.bookName.setText(book.name)
        // TODO: do the same for all data

        //Add the book to your favourites
        binding.addToFavorite.setOnClickListener {

            db.insertFavorite(MainActivity.userId, bookId!!)
        }
        return binding.root
    }

    companion object {
        fun newInstance(bookId: Int) =
            BookDescriptionFragment().apply {
                arguments = Bundle().apply {
                    putInt(BOOK_ID, bookId)
                }
            }
    }
}