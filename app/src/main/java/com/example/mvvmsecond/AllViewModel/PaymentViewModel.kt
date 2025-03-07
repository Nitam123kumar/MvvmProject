package com.example.mvvmsecond.AllViewModel

import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.razorpay.Checkout
import org.json.JSONException
import org.json.JSONObject
import java.util.UUID

class PaymentViewModel: ViewModel() {

    fun payment(price:String,activity: Activity){

            val co = Checkout()
            co.setKeyID("rzp_test_7QRDUFvP75uLJS")
            val amount = (price.replace("₹", "").toDouble() * 100).toInt()



            try {
                val options = JSONObject()
                options.put("name", "Razorpay Corp")
                options.put("description", "Demoing Charges")
                options.put("image", "http://example.com/image/rzp.jpg")
                options.put("theme.color", "#3399cc")
                options.put("currency", "INR")
                options.put("amount", amount)

                val retryObj = JSONObject()
                retryObj.put("enabled", true)
                retryObj.put("max_count", 4)
                options.put("retry", retryObj)
                val prefill = JSONObject()
                prefill.put("email", "gaurav.kumar@example.com")
                prefill.put("contact", "9876543210")

                options.put("prefill", prefill)
                co.open(activity, options)
            } catch (e: JSONException) {
                Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }
        }



}