package com.example.finalproject

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.finalproject.databinding.ActivityMainBinding
import com.example.finalproject.fragment.homeFragment.MainFragment

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQ_CAMERA = 100
        const val REQ_GALLERY = 200

        fun swipeFragment(activity: FragmentActivity, fragment: Fragment) {
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, fragment).commit()
        }
    }

    lateinit var imageView:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        swipeFragment(this, MainFragment())

    }


    fun cameraBtn(imageView: ImageView){
        this.imageView = imageView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(android.Manifest.permission.CAMERA), REQ_CAMERA)
            } else
                openCamera()
        } else {
            openCamera()
        }
    }

    fun galleryBtn(imageView: ImageView){
        this.imageView = imageView
        val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(i, REQ_GALLERY)
    }


    fun openCamera() {
        val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(i, REQ_CAMERA)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ_CAMERA && resultCode == RESULT_OK && data != null) {

            val bitmap = data.extras!!.get("data") as Bitmap
            imageView.setImageBitmap(bitmap)

        } else if (requestCode == REQ_GALLERY && resultCode == RESULT_OK && data != null) {
            val uri = data.data
            Log.e("hzm", uri.toString())
            imageView.setImageURI(uri)

            //Uri.parse("tel:052255665")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQ_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            }else {
                Toast.makeText(this, "Access denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

}