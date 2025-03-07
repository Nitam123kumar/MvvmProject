package com.example.mvvmsecond.Fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmsecond.AllViewModel.UpLoadProductViewModel
import com.example.mvvmsecond.R
import com.example.mvvmsecond.databinding.FragmentUpLoadProductBinding

class UpLoadProductFragment : Fragment() {
        lateinit var binding: FragmentUpLoadProductBinding
        lateinit var viewModel: UpLoadProductViewModel
        var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentUpLoadProductBinding.inflate(inflater,container,false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[UpLoadProductViewModel::class.java]

        binding.productImageView.setOnClickListener {
            selectImage()
        }

        binding.summitButton.setOnClickListener {

            if (binding.tittleEditText.text.toString().isNotEmpty() && binding.priceEditText.text.toString().isNotEmpty() && binding.descriptionEditText.text.toString().isNotEmpty() && imageUri != null){
                viewModel.upLoadProducts(requireContext(),binding.tittleEditText.text.toString(),binding.priceEditText.text.toString(),binding.descriptionEditText.text.toString(),imageUri!!).apply {
                    binding.tittleEditText.text?.clear()
                    binding.priceEditText.text?.clear()
                    binding.descriptionEditText.text?.clear()
                    binding.productImageView.setImageResource(R.drawable.img_1)
                }

            }
            else{
                binding.tittleEditText.error = "Enter Product Tittle"
                binding.priceEditText.error = "Enter Product Price"
                binding.descriptionEditText.error = "Enter Product Description"
            }

        }

        return view

    }

    private fun selectImage() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, 100)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data!!
            binding.productImageView.setImageURI(imageUri)
        }
    }

}