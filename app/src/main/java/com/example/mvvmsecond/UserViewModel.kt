package com.example.mvvmsecond

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class UserViewModel : ViewModel() {
    val user = MutableLiveData<UserViewDataModel>()
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseDatabase.getInstance()

    fun resister(name: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                user.value = UserViewDataModel(name, email)
                val data = hashMapOf(
                    "image" to "",
                    "username" to name,
                    "email" to email
                )
                val user = auth.currentUser?.uid
                db.getReference("mvvmUsersAccount").child(user.toString()).setValue(data)
            }
            .addOnFailureListener {
            }
    }

    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val currentUser = auth.currentUser
                if (currentUser != null) {
                    user.value =
                        UserViewDataModel(currentUser.displayName ?: "", currentUser.email ?: "")
                }
            }
            .addOnFailureListener {
            }
    }

}