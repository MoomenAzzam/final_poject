package com.example.finalproject.fragment.signFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.finalproject.MainActivity
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentSignInBinding
import com.example.finalproject.databinding.FragmentSignUpBinding

class SignInFragment : Fragment() {

    val binding = FragmentSignInBinding.inflate(layoutInflater)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val singInView = inflater.inflate(R.layout.fragment_sign_in, container, false)

        binding.btnLogin.setOnClickListener {
            if(binding.txtUserName.text.toString().isNotEmpty() && binding.txtPassword.text.toString().isNotEmpty()){
                val username = binding.txtUserName.text.toString()
                val password = binding.txtPassword.text.toString()

                //Here the process of comparing the inputs with the values in the data base
                if(true){
                    //If the values are the same, it will be moved to the main Activity
                    val i = Intent(this.requireContext(),MainActivity::class.java)
                    startActivity(i)
                }

            }else
                Toast.makeText(context , "Please Fill in The Required Fields" , Toast.LENGTH_SHORT).show()
        }

//************************************************** The event that occurs when you click on the Sign Up text ****************
        binding.btnSingup.setOnClickListener {
            MainActivity.swipeFragment(requireActivity(),SignUpFragment())
        }


        return singInView


    }


}