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
import com.example.finalproject.databinding.FragmentBookDescriptionBinding
import com.example.finalproject.databinding.FragmentBorrowerBinding


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val BOOK_ID = "bookId"

class BookDescriptionFragment : Fragment() {

    lateinit var binding: FragmentBookDescriptionBinding
    lateinit var db:DatabaseHelper

    private var bookId: Int? = null
    private var isEditing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            this.bookId = it.getInt(BOOK_ID)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBookDescriptionBinding.inflate(inflater, container, false)
        db = DatabaseHelper(requireContext())



        return binding.root
    }


    override fun onResume() {
        super.onResume()

        //hide camera and gallery buttons
        binding.btnAddImgFromCamera.visibility = View.INVISIBLE;
        binding.btnAddImgFromGallery.visibility = View.INVISIBLE;

        //disable spinners
        binding.spinnerCategory.isEnabled = false
        binding.spinnerLanguage.isEnabled = false


        //the spinners lists
        val categoryArr: List<String> = resources.getStringArray(R.array.category).toList();
        val languageArr: List<String> = resources.getStringArray(R.array.language).toList();

        val book = db.getBook(bookId!!)

        binding.bookName.setText(book.name)
        binding.spinnerCategory.setSelection(categoryArr.indexOf(book.category))
        binding.authorName.setText(book.author)
        binding.spinnerLanguage.setSelection(languageArr.indexOf(book.language))
        binding.numberOfPages.setText(book.numberOfPages.toString())
        binding.shelfNumber.setText(book.shelfNumber)
        binding.NumberOfCopiesOfBooks.setText(book.numberOfCopies.toString())
        binding.releaseYear.setText(book.releaseYear.toString())
        binding.description.setText(book.description)
        binding.imgBook.setImageBitmap(book.image)


        //Add the book to your favourites
        var isFavorite = db.isFavorite(MainActivity.userId, bookId!!)

        fun setFavoriteBtnText() {
            if (isFavorite) {
                binding.addToFavorite.text = "Remove from Favorite"
            } else {
                binding.addToFavorite.text = "Add to Favorite"
            }
        }


        //setting the starting text to the favorite btn
        setFavoriteBtnText()


        binding.addToFavorite.setOnClickListener {

            if (!isFavorite) {
                //add to favorite
                if (db.insertFavorite(MainActivity.userId, bookId!!)) {
                    Toast.makeText(requireContext(), "Added to Favorite", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "error while adding to favorite", Toast.LENGTH_SHORT).show()
                }
            }else{
                //remove from favorite
                if(db.deleteFavorite(MainActivity.userId, bookId!!)) {
                    Toast.makeText(requireContext(), "Removed to Favorite", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "error while Removing from favorite", Toast.LENGTH_SHORT).show()
                }
            }

            isFavorite = !isFavorite
            setFavoriteBtnText()
        }


        //edit & save button
        binding.btnEdit.setOnClickListener {
            isEditing = !isEditing


            if (isEditing) {
                binding.btnEdit.text = "save"

                binding.bookName.isEnabled = true
                binding.spinnerCategory.isEnabled = true
                binding.authorName.isEnabled = true
                binding.spinnerLanguage.isEnabled = true
                binding.numberOfPages.isEnabled = true
                binding.shelfNumber.isEnabled = true
                binding.NumberOfCopiesOfBooks.isEnabled = true
                binding.releaseYear.isEnabled = true
                binding.description.isEnabled = true

                //Show camera and gallery buttons
                binding.btnAddImgFromCamera.visibility = View.VISIBLE;
                binding.btnAddImgFromGallery.visibility = View.VISIBLE;

            } else {
                //pressed the save btn
                binding.btnEdit.text = "edit"

                binding.bookName.isEnabled = false
                binding.spinnerCategory.isEnabled = false
                binding.authorName.isEnabled = false
                binding.spinnerLanguage.isEnabled = false
                binding.numberOfPages.isEnabled = false
                binding.shelfNumber.isEnabled = false
                binding.NumberOfCopiesOfBooks.isEnabled = false
                binding.releaseYear.isEnabled = false
                binding.description.isEnabled = false

                //hide camera and gallery buttons
                binding.btnAddImgFromCamera.visibility = View.INVISIBLE;
                binding.btnAddImgFromGallery.visibility = View.INVISIBLE;

                if (binding.bookName.text.toString()
                        .isNotEmpty() && binding.authorName.text.toString()
                        .isNotEmpty() && binding.numberOfPages.text.toString()
                        .isNotEmpty() && binding.shelfNumber.text.toString().isNotEmpty() &&
                    binding.NumberOfCopiesOfBooks.text.toString()
                        .isNotEmpty() && binding.releaseYear.text.toString().isNotEmpty() &&
                    binding.description.text.toString().isNotEmpty()
                ) {

                    val bookName = binding.bookName.text.toString()
                    val category = binding.spinnerCategory.selectedItem.toString()
                    val authorName = binding.authorName.text.toString()
                    val language = binding.spinnerLanguage.selectedItem.toString()
                    val pagesNum = binding.numberOfPages.text.toString().toInt()
                    val shelfNumber = binding.shelfNumber.text.toString()
                    val numberOfCopiesOfBooks = binding.NumberOfCopiesOfBooks.text.toString().toInt()
                    val releaseYear = binding.releaseYear.text.toString().toInt()
                    val description = binding.description.text.toString()
                    val bitmap = (binding.imgBook.drawable as BitmapDrawable).bitmap


                    val db = DatabaseHelper(requireActivity())
                    if(db.updateBook(bookId!!, bookName, category, authorName, language, pagesNum, shelfNumber,
                            numberOfCopiesOfBooks, releaseYear, description, bitmap)){
                        Toast.makeText(requireContext(), "saved", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(requireContext(), "error while saving", Toast.LENGTH_SHORT).show()
                    }

                } else
                    Toast.makeText(
                        context,
                        "Please make sure to fill in all fields",
                        Toast.LENGTH_SHORT
                    ).show()

            }
        }


        binding.btnBorrowers.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, BorrowerFragment.newInstance(bookId!!))
                .commit()
        }


        binding.btnAddImgFromCamera.setOnClickListener {
            (requireActivity() as MainActivity).cameraBtn(binding.imgBook)
        }

        binding.btnAddImgFromGallery.setOnClickListener {
            (requireActivity() as MainActivity).galleryBtn(binding.imgBook)
        }

//
//        binding.imgBook.setOnClickListener {
//            (requireActivity() as MainActivity).cameraBtn(binding.imgBook)
//        }

    }


    companion object {
        fun newInstance(bookId: Int) =
            BookDescriptionFragment().apply {
                arguments = Bundle().apply {
                    putInt(BOOK_ID, bookId)
                }
            }
    }
}