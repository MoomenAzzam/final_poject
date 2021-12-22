package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.finalproject.databinding.ActivityUserSignBinding

class UserSignActivity : AppCompatActivity() {

    companion object{
        fun swipeFragment(activity: FragmentActivity, fragment: Fragment) {
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentSignContainer, fragment).commit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityUserSignBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("MyPref", AppCompatActivity.MODE_PRIVATE)
        val hasSignedInBefore = prefs.getBoolean("hasSignedInBefore", false)
        val editor = prefs.edit()
        if (hasSignedInBefore) {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
    }
}