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
import com.example.finalproject.R
import com.example.finalproject.adapter.RV_book_adapter
import com.example.finalproject.database.DatabaseHelper
import com.example.finalproject.databinding.FragmentMainBinding
import android.widget.AdapterView


class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentMainBinding.inflate(inflater, container, false)
        val db = DatabaseHelper(requireContext())

        //the recycler view adapter
        val adapter = RV_book_adapter(db.getAllBooks())
        binding.rvView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvView.adapter = adapter

        //searching for book
        binding.txtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                (binding.rvView.adapter as RV_book_adapter).search(s.toString())
            }

        })

        //searching using category
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                if (binding.spinner.selectedItem.toString() == "all") {
                    //get all books
                    (binding.rvView.adapter as RV_book_adapter).searchCategory("")
                }else{
                    (binding.rvView.adapter as RV_book_adapter).searchCategory(binding.spinner.selectedItem.toString())
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                return
            }
        }

        binding.btnBookAdd.setOnClickListener {
            MainActivity.swipeFragment(requireActivity(), AddBookFragment())

        }

        binding.root.setOnClickListener {
            MainActivity.swipeFragment(requireActivity(), BookDescriptionFragment())
        }

        return binding.root
    }

}