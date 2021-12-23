package com.example.finalproject.fragment.homeFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalproject.MainActivity
import com.example.finalproject.adapter.BookAdapter
import com.example.finalproject.database.DatabaseHelper
import com.example.finalproject.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        //the recycler view adapter
        val binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val db = DatabaseHelper(requireContext())

        val adapter = BookAdapter(db.getAllFavoritesForUser(MainActivity.userId))
        binding.rvFavorite.layoutManager = GridLayoutManager(requireContext(),2)
        binding.rvFavorite.adapter = adapter

        return binding.root
    }

}