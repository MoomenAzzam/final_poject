package com.example.finalproject.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.database.DatabaseHelper
import com.example.finalproject.databinding.CardViewBinding
import com.example.finalproject.model.Book


class RV_book_adapter(var data: ArrayList<Book>): RecyclerView.Adapter<RV_book_adapter.MyViewHolder>() {
    lateinit var context: Context
    private var initialData = data

    class MyViewHolder(val cardViewBinding: CardViewBinding): RecyclerView.ViewHolder(cardViewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val binding : CardViewBinding
                = CardViewBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.cardViewBinding.apply {
            txtBookName.text = data[position].name
            txtAuthorName.text = data[position].author
            imgBookCover.setImageURI(Uri.parse(data[position].image))
        }


        holder.cardViewBinding.root.setOnLongClickListener {
            AlertDialog.Builder(context).apply {
                setTitle("Delete book")
                setMessage("Are you sure that you want to delete this book?")
                setPositiveButton("Yse"){
                        _, _ ->
                    if(DatabaseHelper(context).deleteBook(data[position].id)){
                        data.removeAt(position)
                        notifyDataSetChanged()
                    }

                }
                setCancelable(true)
                setNegativeButton("No"){ dialogInterface: DialogInterface, _ ->
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

    fun search(text : String){
        val newArray = initialData.filter { book ->
            book.name.contains(text) || book.author.startsWith(text)
        }
        data = newArray as ArrayList<Book>
        notifyDataSetChanged()
    }



}