package com.example.finalproject.fragment.homeFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.adapter.BookAdapter
import com.example.finalproject.adapter.BorrowerAdapter
import com.example.finalproject.database.DatabaseHelper
import com.example.finalproject.databinding.FragmentBorrowerBinding
import com.example.finalproject.databinding.FragmentFavoriteBinding
import com.example.finalproject.fragment.homeFragment.BookDescriptionFragment

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val BOOK_ID = "bookId"

class BorrowerFragment : Fragment() {

    private var bookId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            this.bookId = it.getInt(BOOK_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentBorrowerBinding.inflate(inflater, container, false)

        val db = DatabaseHelper(requireContext())

        val adapter = BorrowerAdapter(db.getAllBorrowersForBook(bookId!!))
        binding.rvMetaphor.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMetaphor.adapter = adapter

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