package com.example.mvvmsecond.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmsecond.DataModels.UserProfileDataModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class GetUserViewModel: ViewModel() {
    val user = MutableLiveData<UserProfileDataModel>()
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseDatabase.getInstance()

    fun getUserProfileData() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            db.getReference("mvvmUsersAccount").child(userId).addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val userData = snapshot.getValue(UserProfileDataModel::class.java)
                        user.value = userData
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    user.value = null
                }
            })
        }
    }

}