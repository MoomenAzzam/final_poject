package com.example.finalproject.fragment.homeFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.R
import com.example.finalproject.database.DatabaseHelper
import com.example.finalproject.databinding.FragmentBookDescriptionBinding
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
        return binding.root
    }

}