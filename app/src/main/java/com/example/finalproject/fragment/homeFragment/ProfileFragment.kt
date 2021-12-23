package com.example.finalproject.fragment.homeFragment

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
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
import com.example.finalproject.databinding.FragmentBookDescriptionBinding
import com.example.finalproject.databinding.FragmentProfileBinding

private const val USER_ID = "userId"

class ProfileFragment : Fragment() {

    private var userId = MainActivity.userId
    private var isEditing = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding = FragmentProfileBinding.inflate(inflater, container, false)
        val db = DatabaseHelper(requireContext())

        val profile = db.getUser(userId!!)
        binding.tvName.setText(profile.name)
        binding.tvEmail.setText(profile.email)
        binding.tvDOB.setText(profile.dob)

        binding.btnEdit.setOnClickListener {
            isEditing = !isEditing
            //When pressed, the user will be given the ability to modify and save the modified data as well
            if (isEditing) {
                binding.btnEdit.text = "save"

                binding.tvName.isEnabled = true
                binding.tvEmail.isEnabled = true
                binding.tvDOB.isEnabled = true

                if(binding.tvName.text.toString().isNotEmpty() &&
                    binding.tvEmail.text.toString().isNotEmpty() &&
                        binding.tvDOB.text.isNotEmpty() ){

                    val personName = binding.tvName.text.toString()
                    val personEmail = binding.tvEmail.text.toString()
                    val personDOB = binding.tvDOB.text.toString()

                    val img = (binding.personImg as BitmapDrawable).bitmap
                    db.updateUser(userId, personName, personEmail,0.toString(), personDOB, img)
                }else
                    Toast.makeText(context, "Please make sure to fill in all fields", Toast.LENGTH_SHORT).show()
            }
    }

        binding.btnLogout.setOnClickListener {
            Toast.makeText(context, "logged out", Toast.LENGTH_SHORT).show()
            val prefs = requireContext().getSharedPreferences("MyPref", AppCompatActivity.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putBoolean("hasSignedInBefore", false).apply()
            val i = Intent(requireContext(), UserSignActivity::class.java)
            startActivity(i)
        }

        return binding.root
    }


}