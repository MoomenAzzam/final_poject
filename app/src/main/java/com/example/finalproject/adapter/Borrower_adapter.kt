package com.example.finalproject.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.database.DatabaseHelper
import com.example.finalproject.databinding.BookCardViewBinding
import com.example.finalproject.databinding.BorrowerCardViewBinding
import com.example.finalproject.fragment.homeFragment.BookDescriptionFragment
import com.example.finalproject.model.Book
import com.example.finalproject.model.Borrower

class Borrower_adapter(var data: ArrayList<Borrower>) :
    RecyclerView.Adapter<Borrower_adapter.MyViewHolder>() {
    lateinit var context: Context
    private var initialData = data

    class MyViewHolder(val cardViewBinding: BorrowerCardViewBinding) :
        RecyclerView.ViewHolder(cardViewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val binding: BorrowerCardViewBinding =
            BorrowerCardViewBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.cardViewBinding.apply {
           txtName.text = data[position].name
           txtDate.text = data[position].borrowDate
        }


        holder.cardViewBinding.root.setOnLongClickListener {
            AlertDialog.Builder(context).apply {
                setTitle("Delete borrower")
                setMessage("Are you sure that you want to delete this borrower?")
                setPositiveButton("Yse") { _, _ ->
                    if (DatabaseHelper(context).deleteBook(data[position].id)) {
                        data.removeAt(position)
                        notifyDataSetChanged()
                    }

                }
                setCancelable(true)
                setNegativeButton("No") { dialogInterface: DialogInterface, _ ->
                    dialogInterface.cancel()
                }
                create()
                show()
            }
            true
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }


}



