package com.example.finalproject.fragment.signFragment

import android.annotation.SuppressLint
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.finalproject.MainActivity
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.R
import com.example.finalproject.UserSignActivity
import com.example.finalproject.database.DatabaseHelper
import com.example.finalproject.databinding.FragmentSignUpBinding


class SignUpFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSignUpBinding.inflate(inflater, container, false)

        binding.btnSignUp.setOnClickListener {
            //The button that when pressed saves the information of the new user
            if (binding.tvName.text.isNotEmpty() && binding.tvPassword.text.isNotEmpty()
                && binding.tvRePassword.text.isNotEmpty() && binding.tvEmail.text.isNotEmpty()
            ) {
              //  binding.btnSignUp.setOnClickListener {
                    val username = binding.tvName.text.toString()
                    val email = binding.tvEmail.text.toString()
                    val pass = binding.tvPassword.text.toString()
                    val rePass = binding.tvPassword.text.toString()

                    //To check whether the password in the first field is the same as in the second field
                    if (pass == rePass) {
                        val db = DatabaseHelper(requireContext())
                        val bitmap = (binding.imgUser.drawable as BitmapDrawable).bitmap
                        db.insertUser(username, email, pass, bitmap)

                        UserSignActivity.swipeFragment(requireActivity(), SignInFragment())
                    } else {
                        Toast.makeText(
                            context,
                            "Please verify that the password is correct",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
               // }
            } else
                Toast.makeText(
                    context,
                    "Please Fill in The Required Fields",
                    Toast.LENGTH_SHORT
                ).show()
        }


        binding.btnAddImgFromGallery.setOnClickListener {
            (requireActivity() as UserSignActivity).galleryBtn(binding.imgUser)
        }

        binding.btnAddImgFromCamera.setOnClickListener {
            (requireActivity() as UserSignActivity).cameraBtn(binding.imgUser)
        }

//******************************************************  signInLink  *********************************************
        // The text that the user will click on if they already have an account
        binding.singInLink.setOnClickListener {
            UserSignActivity.swipeFragment(requireActivity(), SignInFragment())
        }

        return binding.root
    }

}
