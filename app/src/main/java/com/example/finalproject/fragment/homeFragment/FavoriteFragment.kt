package com.example.finalproject.fragment.homeFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalproject.R
import com.example.finalproject.adapter.RV_book_adapter
import com.example.finalproject.database.DatabaseHelper
import com.example.finalproject.databinding.FragmentFavoriteBinding
import com.example.finalproject.databinding.FragmentMainBinding

class FavoriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        //the recycler view adapter
        val binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val db = DatabaseHelper(requireContext())

        val prefs = requireActivity().getSharedPreferences("MyPref", AppCompatActivity.MODE_PRIVATE)
        val userId = prefs.getInt("userId", -1)

        val adapter = RV_book_adapter(db.getAllFavoritesForUser(userId))
        binding.rvFavorite.layoutManager = GridLayoutManager(requireContext(),2)
        binding.rvFavorite.adapter = adapter

        return binding.root
    }

}