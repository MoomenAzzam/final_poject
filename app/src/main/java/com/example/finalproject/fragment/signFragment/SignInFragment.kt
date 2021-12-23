package com.example.finalproject.fragment.signFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.MainActivity
import com.example.finalproject.R
import com.example.finalproject.UserSignActivity
import com.example.finalproject.database.DatabaseHelper
import com.example.finalproject.databinding.FragmentSignInBinding
import com.example.finalproject.databinding.FragmentSignUpBinding

class SignInFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSignInBinding.inflate(inflater, container, false)

        binding.btnLogin.setOnClickListener {
            if (binding.txtUserName.text.toString()
                    .isNotEmpty() && binding.txtPassword.text.toString().isNotEmpty()
            ) {
                val username = binding.txtUserName.text.toString()
                val password = binding.txtPassword.text.toString()

                //Here the process of comparing the inputs with the values in the data base
                val db = DatabaseHelper(requireContext())
                val users = db.getAllUsers()
                var foundUser = false
                for (user in users) {
                    if ((username == user.name || username == user.email) && password == user.password) {
                        foundUser = true

                        //shared pref
                        val prefs = requireActivity().getSharedPreferences("MyPref", AppCompatActivity.MODE_PRIVATE)
                        val editor = prefs.edit()
                        editor.putBoolean("hasSignedInBefore", true).apply()
                        editor.putInt("userId", user.id).apply()
                        val i = Intent(this.requireContext(), MainActivity::class.java)
                        startActivity(i)
                        requireActivity().finish()
                        break

                    }
                }

                if(!foundUser){
                    Toast.makeText(
                        requireContext(),
                        "the username or password your entered in incorrect",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } else
                Toast.makeText(context, "Please Fill in The Required Fields", Toast.LENGTH_SHORT)
                    .show()
        }

//************************************************** The event that occurs when you click on the Sign Up text ****************
        binding.btnSingup.setOnClickListener {
            UserSignActivity.swipeFragment(requireActivity(), SignUpFragment())
        }


        return binding.root


    }


}