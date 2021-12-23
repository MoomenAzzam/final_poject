package com.example.finalproject.fragment.homeFragment

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
            this.bookId = it.getInt(BOOK_ID)
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

        binding.btnEdit.setOnClickListener {
            binding.btnEdit.text = "save"

            binding.bookName.isEnabled = true
            binding.spinnerCategory.isEnabled =true
            binding.authorName.isEnabled = true
            binding.spinnerLanguage.isEnabled = true
            binding.numberOfPages.isEnabled = true
            binding.shelfNumber.isEnabled = true
            binding.NumberOfCopiesOfBooks.isEnabled = true
            binding.releaseYear.isEnabled = true
            binding.description.isEnabled = true

            if(binding.bookName.text.toString().isNotEmpty() && binding.spinnerCategory.text.toString().isNotEmpty() &&
                binding.authorName.text.toString().isNotEmpty()&& binding.spinnerLanguage.text.toString().isNotEmpty() &&
                binding.numberOfPages.text.toString().isNotEmpty() && binding.shelfNumber.text.toString().isNotEmpty() &&
                binding.NumberOfCopiesOfBooks.text.toString().isNotEmpty() && binding.releaseYear.text.toString().isNotEmpty() &&
                binding.description.text.toString().isNotEmpty()){

                val bookName = binding.bookName.text.toString()

                val category = binding.spinnerCategory.text.toString()
                val authorName = binding.authorName.text.toString()
                val language = binding.spinnerLanguage.text.toString()
                val pagesNum = binding.numberOfPages.text.toString()
                val shelfNumber = binding.shelfNumber.text.toString()
                val NumberOfCopiesOfBooks = binding.NumberOfCopiesOfBooks.text.toString()
                val releaseYear = binding.releaseYear.text.toString()
                val description = binding.description.text.toString()

                var test = true
                try{
                    val pagesIsANumber = Integer.parseInt(pagesNum)
                    val copiesIsANumber = Integer.parseInt(NumberOfCopiesOfBooks)
                    val YearIsANumber = Integer.parseInt(releaseYear)
                }catch (e: NumberFormatException){
                    test = false
                }

                if(test){
                    val db = DatabaseHelper(requireActivity())
                    val img = (binding.imgBook as BitmapDrawable).bitmap
                    db.updateBook(bookId!!, bookName,authorName,description, category, img)

                }else
                    Toast.makeText(context, "Please make sure that the numbers are entered correctly", Toast.LENGTH_SHORT).show()

            }else
                Toast.makeText(context, "Please make sure to fill in all fields", Toast.LENGTH_SHORT).show()

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