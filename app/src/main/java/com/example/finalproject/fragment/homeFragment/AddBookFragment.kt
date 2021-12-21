package com.example.finalproject.fragment.homeFragment

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalproject.MainActivity
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentAddBinding
import com.example.finalproject.databinding.FragmentMainBinding

class AddBookFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentAddBinding.inflate(inflater, container, false)

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