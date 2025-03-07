package com.example.mvvmsecond

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mvvmsecond.AllViewModel.PaymentViewModel
import com.example.mvvmsecond.databinding.ActivityPementBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import org.json.JSONException
import org.json.JSONObject
import java.util.UUID

class PaymentActivity : AppCompatActivity(), PaymentResultWithDataListener {
    lateinit var binding: ActivityPementBinding
    val hasMap = HashMap<String,String>()
    val db = FirebaseDatabase.getInstance()
    val id = UUID.randomUUID().toString()
    lateinit var viewModel: PaymentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPementBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[PaymentViewModel::class.java]

        val productTittle = intent.getStringExtra("tittle")
        val productPrice = intent.getStringExtra("price")
        val productDescription = intent.getStringExtra("description")
        val productImage = intent.getStringExtra("image")

        hasMap["orderId"] = id
        hasMap["orderTittle"] = productTittle.toString()
        hasMap["orderDescription"] = productDescription.toString()
        hasMap["orderProductPrice"] = productPrice.toString()
        hasMap["orderImage"] = productImage.toString()

        binding.proceedBtn.setOnClickListener {
           viewModel.payment(productPrice.toString(),this)
        }

        Glide.with(this).load(productImage).placeholder(R.drawable.img_5).into(binding.cartImageView)
        binding.productTittleTV.text = title
        binding.productDesTV.text = productDescription
        binding.priceTextView.text = productPrice
        binding.priceTextView1.text = productPrice
        binding.priceTextView2.text = productPrice




        binding.backOn.setOnClickListener {
            finish()
        }



    }


    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show()
        val uuid= FirebaseAuth.getInstance().currentUser?.uid
        db.getReference("mvvmProducts").child("myOrder").child(uuid!!).child(id).setValue(hasMap)
            .addOnSuccessListener {
                Toast.makeText(this, "myOrder Successful", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "myOrder Failed", Toast.LENGTH_SHORT).show()
            }

    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show()
    }
}