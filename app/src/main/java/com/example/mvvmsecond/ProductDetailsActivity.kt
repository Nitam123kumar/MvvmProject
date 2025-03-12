package com.example.mvvmsecond

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mvvmsecond.ViewModels.ProductDetailsViewModel
import com.example.mvvmsecond.databinding.ActivityProductDetialsBinding

class ProductDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductDetialsBinding
    lateinit var viewModel: ProductDetailsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProductDetialsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[ProductDetailsViewModel::class.java]

        val productTittle = intent.getStringExtra("productTittle")
        val productPrice = intent.getStringExtra("productPrice")
        val productDescription = intent.getStringExtra("productDescription")
        val productImage = intent.getStringExtra("productImage")

        binding.productName.text = productTittle
        binding.originalPrice.text = productPrice
        binding.productDetails1.text = productDescription
        Glide.with(this).load(productImage).into(binding.productImage)

        binding.goToCart.setOnClickListener {
            viewModel.addToCart(
                this,
                productTittle!!,
                productPrice!!,
                productDescription!!,
                productImage!!
            )
            onResume().apply {
                finish()
            }
        }

        binding.buyNow.setOnClickListener {
            val intent = Intent(this, PaymentActivity::class.java)
            intent.putExtra("tittle", productTittle)
            intent.putExtra("price", productPrice)
            intent.putExtra("description", productDescription)
            intent.putExtra("image", productImage)
            startActivity(intent)
        }
    }
}
