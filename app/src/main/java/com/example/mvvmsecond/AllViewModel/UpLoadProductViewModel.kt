package com.example.mvvmsecond.AllViewModel

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmsecond.AllDataModel.UpLoadProductDataModel
import com.google.firebase.Timestamp
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class UpLoadProductViewModel:ViewModel() {
     val products = MutableLiveData<UpLoadProductDataModel>()
    val db = FirebaseDatabase.getInstance()
    private val storageRef = FirebaseStorage.getInstance().reference
    var randomId = UUID.randomUUID().toString()



    fun insertProduct(context: Context,productTittle:String,productPrice:String,productDescription:String,productImage:String){

        val HashMap = HashMap<String,String>()
        HashMap["productId"] = randomId
        HashMap["productTittle"] = productTittle
        HashMap["productPrice"] = productPrice
        HashMap["productDescription"] = productDescription
        HashMap["productImage"] = productImage

        db.getReference("mvvmProducts").child(randomId).setValue(HashMap)
            .addOnSuccessListener {
                Toast.makeText(context, "Insert Success", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Insert Failed", Toast.LENGTH_SHORT).show()
            }

    }

    fun upLoadProducts(context: Context,productTittle:String,productPrice:String,productDescription:String,productImage: Uri){
        val imageName = "${Timestamp.now().nanoseconds}.png"
        val imageRef = storageRef.child("mvvmProducts/$imageName")

        imageRef.putFile(productImage)
            .addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                    val imageUrl = uri.toString()
                    insertProduct(context,productTittle,productPrice,productDescription,imageUrl)
                    Toast.makeText(context, "UpLoad Success", Toast.LENGTH_SHORT).show()

                }
                    .addOnFailureListener {
                        Toast.makeText(context, "UpLoad Failed", Toast.LENGTH_SHORT).show()

                    }

            }


    }

}