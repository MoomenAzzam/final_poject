package com.example.finalproject.fragment.homeFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.adapter.BookAdapter
import com.example.finalproject.adapter.BorrowerAdapter
import com.example.finalproject.database.DatabaseHelper
import com.example.finalproject.databinding.FragmentBorrowerBinding
import com.example.finalproject.databinding.FragmentFavoriteBinding
import com.example.finalproject.fragment.homeFragment.BookDescriptionFragment
import com.example.finalproject.model.Borrower

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

        val borrowers = db.getAllBorrowersForBook(bookId!!)
        val adapter = BorrowerAdapter(borrowers)
        binding.rvBorrower.layoutManager = LinearLayoutManager(requireContext())
        binding.rvBorrower.adapter = adapter
        
        binding.btnAddBorrower.setOnClickListener { 
            if(binding.tvBorrowerName.text.isNotEmpty()){
                if(db.insertBorrower(bookId!!, binding.tvBorrowerName.text.toString())){
                    Toast.makeText(requireContext(), "Added borrower", Toast.LENGTH_SHORT).show()
                    var book = db.getBook(bookId!!)
                    //decrease the number of copies by one
                    db.updateBook(book.id,book.name,book.category,book.author,book.language,book.numberOfPages,book.shelfNumber,
                        book.numberOfCopies - 1 ,book.releaseYear,book.description,book.image)
                    //update the data
                    val borrowers = db.getAllBorrowersForBook(bookId!!)
                    binding.rvBorrower.adapter = BorrowerAdapter(borrowers)

                    (binding.rvBorrower.adapter as BorrowerAdapter).notifyDataSetChanged()

                    binding.tvBorrowerName.setText("")
                }else{
                    Toast.makeText(requireContext(), "Error while adding the borrower", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(), "Pleas fill the name field", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    companion object {
        fun newInstance(bookId: Int) =
            BorrowerFragment().apply {
                arguments = Bundle().apply {
                    putInt(BOOK_ID, bookId)
                }
            }
    }
}