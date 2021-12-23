package com.example.finalproject.fragment.homeFragment

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.example.finalproject.BorrowerFragment
import com.example.finalproject.MainActivity
import com.example.finalproject.R
import com.example.finalproject.database.DatabaseHelper
import com.example.finalproject.databinding.FragmentBookDescriptionBinding

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val BOOK_ID = "bookId"

class BookDescriptionFragment : Fragment() {

    private var bookId: Int? = null
    private var isEditing = false

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
        binding.spinnerCategory.setText(book.category)
        binding.authorName.setText(book.author)
        binding.spinnerLanguage.setText(book.language)
        binding.numberOfPages.setText(book.numberOfPages.toString())
        binding.shelfNumber.setText(book.shelfNumber)
        binding.NumberOfCopiesOfBooks.setText(book.numberOfCopies.toString())
        binding.releaseYear.setText(book.releaseYear.toString())
        binding.description.setText(book.description)


        //Add the book to your favourites
        var isFavorite = db.isFavorite(MainActivity.userId, bookId!!)

        fun setFavortieBtnText() {
            if (isFavorite) {
                binding.addToFavorite.text = "Remove from Favorite"
            } else {
                binding.addToFavorite.text = "Add to Favorite"
            }
        }

        //setting the starting text to the favorite btn
        setFavortieBtnText()

        binding.addToFavorite.setOnClickListener {

            if (!isFavorite) {
                //add to favorite
                if (db.insertFavorite(MainActivity.userId, bookId!!)) {
                    Toast.makeText(requireContext(), "Added to Favorite", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "error while adding to favorite", Toast.LENGTH_SHORT).show()
                }
            }else{
                //remove from favorite
                if(db.deleteFavorite(MainActivity.userId, bookId!!)) {
                    Toast.makeText(requireContext(), "Removed to Favorite", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "error while Removing from favorite", Toast.LENGTH_SHORT).show()
                }
            }

            isFavorite = !isFavorite
            setFavortieBtnText()
        }


        //edit & save button
        binding.btnEdit.setOnClickListener {
            isEditing = !isEditing

            if (isEditing) {
                binding.btnEdit.text = "save"

                binding.bookName.isEnabled = true
                binding.spinnerCategory.isEnabled = true
                binding.authorName.isEnabled = true
                binding.spinnerLanguage.isEnabled = true
                binding.numberOfPages.isEnabled = true
                binding.shelfNumber.isEnabled = true
                binding.NumberOfCopiesOfBooks.isEnabled = true
                binding.releaseYear.isEnabled = true
                binding.description.isEnabled = true


            } else {
                //pressed the save btn
                if (binding.bookName.text.toString()
                        .isNotEmpty() && binding.spinnerCategory.text.toString().isNotEmpty() &&
                    binding.authorName.text.toString()
                        .isNotEmpty() && binding.spinnerLanguage.text.toString().isNotEmpty() &&
                    binding.numberOfPages.text.toString()
                        .isNotEmpty() && binding.shelfNumber.text.toString().isNotEmpty() &&
                    binding.NumberOfCopiesOfBooks.text.toString()
                        .isNotEmpty() && binding.releaseYear.text.toString().isNotEmpty() &&
                    binding.description.text.toString().isNotEmpty()
                ) {

                    val bookName = binding.bookName.text.toString()

                    val category = binding.spinnerCategory.text.toString()
                    val authorName = binding.authorName.text.toString()
                    val language = binding.spinnerLanguage.text.toString()
                    val pagesNum = binding.numberOfPages.text.toString().toInt()
                    val shelfNumber = binding.shelfNumber.text.toString()
                    val numberOfCopiesOfBooks =
                        binding.NumberOfCopiesOfBooks.text.toString().toInt()
                    val releaseYear = binding.releaseYear.text.toString().toInt()
                    val description = binding.description.text.toString()


                    val db = DatabaseHelper(requireActivity())
                    val img = (binding.imgBook as BitmapDrawable).bitmap
                    db.updateBook(
                        bookId!!, bookName, category, authorName, language, pagesNum, shelfNumber,
                        numberOfCopiesOfBooks, releaseYear, description, img
                    )

                } else
                    Toast.makeText(
                        context,
                        "Please make sure to fill in all fields",
                        Toast.LENGTH_SHORT
                    ).show()

            }
        }

        binding.btnMetaphor.setOnClickListener {
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, BorrowerFragment.newInstance(bookId!!))
                .commit()
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