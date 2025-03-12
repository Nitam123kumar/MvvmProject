package com.example.mvvmsecond

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mvvmsecond.ViewModels.UpdateProfileViewModel
import com.example.mvvmsecond.databinding.ActivityProfileEditBinding

class ProfileEditActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileEditBinding
    lateinit var viewModel: UpdateProfileViewModel
    var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[UpdateProfileViewModel::class.java]

        binding.profileNameEdittext.setText(intent.getStringExtra("username"))
        binding.profileEmailEdittext.text = intent.getStringExtra("email")
       val image = intent.getStringExtra("image")
        Glide.with(this).load(image).into(binding.profileImageEdit)

        viewModel.user.observe(this){
            finish()

        }

        binding.updateProfileButton.setOnClickListener {
            val profileName = binding.profileNameEdittext.text.toString()
            val profileEmail = binding.profileEmailEdittext.text.toString()
            binding.profileImageEdit.setImageURI(imageUri)
            viewModel.upLoadProducts(this,profileName,profileEmail,imageUri)
        }

        binding.openGalleryCardView.setOnClickListener {

        selectImage()

        }
        binding.onBack.setOnClickListener {
            onBackPressed()
        }


    }

    private fun selectImage() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, 100)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data!!
            binding.profileImageEdit.setImageURI(imageUri)
        }
    }
}