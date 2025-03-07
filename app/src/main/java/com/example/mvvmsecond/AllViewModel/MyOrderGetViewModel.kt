package com.example.mvvmsecond.AllViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmsecond.AllDataModel.MyOrderDataModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MyOrderGetViewModel: ViewModel() {
    val productList = MutableLiveData<ArrayList<MyOrderDataModel>>()
    val db = FirebaseDatabase.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid

    fun fetchData() {

        db.getReference("mvvmProducts").child("myOrder").child(userId!!).addValueEventListener(object :
            ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<MyOrderDataModel>()
                for (data in snapshot.children) {
                    try {
                        val item = data.getValue(MyOrderDataModel::class.java)
                        if (item != null) {
                            list.add(item)
                            productList.postValue(list)
                        }
                    } catch (e: Exception) {
                        Log.e("FirebaseError", "Data conversion error: ${e.message}")
                    }}
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseError", "Error fetching cart data: ${error.message}")
            }

        })



    }

}