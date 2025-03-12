package com.example.mvvmsecond.AllViewModel

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmsecond.AllDataModel.AddToCartDataModel
import com.example.mvvmsecond.AllDataModel.UpLoadProductDataModel
import com.example.mvvmsecond.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.UUID

class ProductDetailsViewModel : ViewModel() {
    val product = MutableLiveData<AddToCartDataModel>()
    val db = FirebaseDatabase.getInstance()
    val random = UUID.randomUUID().toString()

    fun addToCart(
        context: Context,
        productTittle: String,
        productPrice: String,
        productDescription: String,
        productImage: String
    ) {

        val HashMap = HashMap<String, String>()
        HashMap["cartId"] = random
        HashMap["cartTittle"] = productTittle
        HashMap["cartPrice"] = productPrice
        HashMap["cartDescription"] = productDescription
        HashMap["cartImage"] = productImage

        db.getReference("mvvmProducts").child("cart")
            .child(FirebaseAuth.getInstance().currentUser!!.uid).child(random).setValue(HashMap)
            .addOnSuccessListener {
                product.value = AddToCartDataModel(
                    cartId = random,
                    cartTittle = productTittle,
                    cartPrice = productPrice,
                    cartDescription = productDescription,
                    cartImage = productImage
                )
                Toast.makeText(context, "AddToCart Success", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, MainActivity::class.java)
                    intent.putExtra("showFragment","showFragment")
                context.startActivity(intent)
            }
            .addOnFailureListener {
                Toast.makeText(context, "AddToCart Failed", Toast.LENGTH_SHORT).show()

            }

    }

}