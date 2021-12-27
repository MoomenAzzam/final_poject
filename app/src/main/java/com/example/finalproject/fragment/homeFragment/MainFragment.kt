package com.example.finalproject.fragment.homeFragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalproject.MainActivity
import com.example.finalproject.adapter.BookAdapter
import com.example.finalproject.database.DatabaseHelper
import com.example.finalproject.databinding.FragmentMainBinding
import android.widget.AdapterView
import com.example.finalproject.databinding.FragmentProfileBinding


class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding
    lateinit var db:DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)
        db = DatabaseHelper(requireContext())

        return binding.root
    }


    override fun onResume() {
        super.onResume()

        //the recycler view adapter
        val adapter = BookAdapter(db.getAllBooks())
        binding.rvView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvView.adapter = adapter

        //searching for book
        binding.txtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                (binding.rvView.adapter as BookAdapter).search(s.toString())
            }

        })

        //searching using category
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                if (binding.spinner.selectedItem.toString() == "All") {
                    //get all books
                    (binding.rvView.adapter as BookAdapter).searchCategory("")
                }else{
                    (binding.rvView.adapter as BookAdapter).searchCategory(binding.spinner.selectedItem.toString())
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                return
            }
        }

        binding.btnBookAdd.setOnClickListener {
            MainActivity.swipeFragment(requireActivity(), AddBookFragment())

        }

//        binding.root.setOnClickListener {
//            MainActivity.swipeFragment(requireActivity(), BookDescriptionFragment())
//        }

    }

}