package com.example.mvvmsecond.AllViewModel

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmsecond.AllDataModel.UserProfileDataModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class UpdateProfileViewModel : ViewModel() {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseDatabase.getInstance()
    val user = MutableLiveData<UserProfileDataModel>()
    private val storageRef = FirebaseStorage.getInstance().reference



    fun insertProduct(
        context: Context,
        profileName: String,
        profileEmail: String,
        profileImage: String
    ) {

        val HashMap = HashMap<String, String>()
        HashMap["username"] = profileName
        HashMap["email"] = profileEmail
        if (profileImage != "") {
            HashMap["image"] = profileImage
        }

        db.getReference("mvvmUsersAccount").child(auth.currentUser?.uid.toString()).updateChildren(
            HashMap as Map<String, Any>
        )
            .addOnSuccessListener {
                user.value = UserProfileDataModel(profileImage, profileName, profileEmail)
                Toast.makeText(context, "Update Success", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Update Failed", Toast.LENGTH_SHORT).show()
            }

    }

    fun upLoadProducts(
        context: Context,
        profileName: String,
        profileEmail: String,
        profileImage: Uri?
    ) {
        if (profileImage != null) {
            val imageRef = storageRef.child("mvvmUsersAccount/${auth.currentUser?.uid.toString()}")

            imageRef.putFile(profileImage)
                .addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                        val imageUrl = uri.toString()
                        insertProduct(context, profileName, profileEmail, imageUrl)
                        Toast.makeText(context, "Update Success", Toast.LENGTH_SHORT).show()

                    }
                        .addOnFailureListener {
                            Toast.makeText(context, "Update Failed", Toast.LENGTH_SHORT).show()

                        }

                }
        } else {
            insertProduct(context, profileName, profileEmail, "")
        }


    }


}