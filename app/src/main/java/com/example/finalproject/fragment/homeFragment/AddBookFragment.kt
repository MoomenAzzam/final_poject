package com.example.finalproject.fragment.homeFragment

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.finalproject.MainActivity
import com.example.finalproject.R
import com.example.finalproject.database.DatabaseHelper
import com.example.finalproject.databinding.FragmentAddBinding
import com.example.finalproject.databinding.FragmentMainBinding
import java.lang.Integer.parseInt

class AddBookFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentAddBinding.inflate(inflater, container, false)

        binding.btnAdd.setOnClickListener {
            if(binding.bookName.text.toString().isNotEmpty() && binding.spinnerCategory.selectedItem.toString().isNotEmpty() &&
            binding.authorName.text.toString().isNotEmpty() && binding.spinnerLanguage.selectedItem.toString().isNotEmpty() &&
            binding.numberOfPages.text.toString().isNotEmpty() && binding.shelfNumber.text.toString().isNotEmpty() &&
            binding.NumberOfCopiesOfBooks.text.toString().isNotEmpty() && binding.releaseYear.text.toString().isNotEmpty() &&
            binding.description.text.toString().isNotEmpty()){

                val bookName = binding.bookName.text.toString()
                val category = binding.spinnerCategory.selectedItem.toString()
                val authorName = binding.authorName.text.toString()
                val language = binding.spinnerLanguage.selectedItem.toString()
                val pagesNum = binding.numberOfPages.text.toString().toInt()
                val shelfNumber = binding.shelfNumber.text.toString()
                val copiesNum = binding.NumberOfCopiesOfBooks.text.toString().toInt()
                val releaseYear = binding.releaseYear.text.toString().toInt()
                val description = binding.description.text.toString()


                //After making sure that all this data is correct, we include this information in the database

                    val db = DatabaseHelper(requireActivity())
                    val bitmap = (binding.imgBook.drawable as BitmapDrawable).bitmap
                    if(db.insertBook(bookName,category,authorName,language,
                            pagesNum,shelfNumber,copiesNum,releaseYear,description,bitmap)) {
                        Toast.makeText(context, "Adding the book succeeded", Toast.LENGTH_SHORT).show()
                    }else
                        Toast.makeText(context, "Adding the book failed", Toast.LENGTH_SHORT).show()


        }else
         Toast.makeText(context, "Please make sure to fill in all fields", Toast.LENGTH_SHORT).show()

    }

//When you press this button, it returns to the interface from which it came, which is the main and cancels the addition process
        binding.btnCancel.setOnClickListener {
            MainActivity.swipeFragment(requireActivity(),MainFragment())
        }

        /*
        this is how to open camera
        (requireActivity() as MainActivity).cameraBtn(binding.imgBook)

        this is how to open gallery
        (requireActivity() as MainActivity).galleryBtn(binding.imgBook)

        this is how to get the image as bitmap to save it in the database
        val bitmap = (binding.imgBook.drawable as BitmapDrawable).bitmap
         */

        return binding.root
    }

}

/*للتاكد اذا المدخل رقم ام لا
 val pagesIsANumber = parseInt(pagesNum)
                val shelfIsANumber = parseInt(shelfNumber)
                val copiesIsANumber = parseInt(copiesNum)

                if(pagesIsANumber ){

                }*/