package com.example.mvvmsecond.Fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmsecond.ViewModels.UpLoadProductViewModel
import com.example.mvvmsecond.R
import com.example.mvvmsecond.databinding.FragmentUpLoadProductBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions

class UpLoadProductFragment : Fragment() {
    lateinit var binding: FragmentUpLoadProductBinding
    lateinit var viewModel: UpLoadProductViewModel
    var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpLoadProductBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[UpLoadProductViewModel::class.java]

        binding.productImageView.setOnClickListener {
            selectImage()
        }

        binding.summitButton.setOnClickListener {
            if (binding.tittleEditText.text.toString().isNotEmpty() &&
                binding.priceEditText.text.toString().isNotEmpty() &&
                binding.descriptionEditText.text.toString().isNotEmpty() && imageUri != null
            ) {
                viewModel.upLoadProducts(
                    requireContext(),
                    binding.tittleEditText.text.toString(),
                    binding.priceEditText.text.toString(),
                    binding.descriptionEditText.text.toString(),
                    imageUri!!
                ).apply {
                    binding.tittleEditText.text?.clear()
                    binding.priceEditText.text?.clear()
                    binding.descriptionEditText.text?.clear()
                    binding.productImageView.setImageResource(R.drawable.img_1)
                }
            } else {
                binding.tittleEditText.error = "Enter Product Title"
                binding.priceEditText.error = "Enter Product Price"
                binding.descriptionEditText.error = "Enter Product Description"
            }
        }

        return binding.root
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
            detectImageLabels(imageUri!!)  // Image label detect karne ke liye function call
        }
    }

    private fun detectImageLabels(uri: Uri) {
        try {
            val image = InputImage.fromFilePath(requireContext(), uri)
            val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)

            labeler.process(image)
                .addOnSuccessListener { labels ->
                    if (labels.isNotEmpty()) {
                        val detectedLabel = labels[0].text // Sabse strong match
                        val confidence = labels[0].confidence

                        Log.d("ML_TAG", "Label: $detectedLabel, Confidence: $confidence")

                        // Title aur Description me auto-fill
                        binding.tittleEditText.setText(detectedLabel)
                        binding.descriptionEditText.setText("This is a $detectedLabel product.")
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("ML_TAG", "Error: ${e.message}")
                }
        } catch (e: Exception) {
            Log.e("ML_TAG", "Error processing image: ${e.message}")
        }
    }
}
