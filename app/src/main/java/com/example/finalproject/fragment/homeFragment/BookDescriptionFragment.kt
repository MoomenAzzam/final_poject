package com.example.finalproject.fragment.homeFragment

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.R
import com.example.finalproject.database.DatabaseHelper
import com.example.finalproject.databinding.FragmentBookDescriptionBinding
import com.example.finalproject.model.Book
import com.example.finalproject.model.Favorite

class BookDescriptionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentBookDescriptionBinding.inflate(inflater,container,false)
//Add the book to your favourites
        binding.addToFavorite.setOnClickListener {
            val db = DatabaseHelper(requireContext())
            val sharedPre = requireActivity().getSharedPreferences("MyPref", AppCompatActivity.MODE_PRIVATE)
            val userId = sharedPre.getInt(Favorite.COL_USER_ID , 0)
            val bookId = sharedPre.getInt(Favorite.COL_BOOK_ID , 0)

            db.insertFavorite(userId,bookId)
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
                    db.updateBook(bookId , bookName,authorName,description, category, img)

                }else
                    Toast.makeText(context, "Please make sure that the numbers are entered correctly", Toast.LENGTH_SHORT).show()

            }else
                Toast.makeText(context, "Please make sure to fill in all fields", Toast.LENGTH_SHORT).show()

        }

        return binding.root
    }

}
/*

        // Inflate the layout for this fragment
        val binding = FragmentBookDescriptionBinding.inflate(inflater,container,false)
//Add the book to your favourites


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
                    val bookId = Book.COL_ID
                    db.updateBook($bookId, bookName,authorName,description, category, img)

                }else
                Toast.makeText(context, "Please make sure that the numbers are entered correctly", Toast.LENGTH_SHORT).show()

    }else
        Toast.makeText(context, "Please make sure to fill in all fields", Toast.LENGTH_SHORT).show()

        }
        return binding.root*/