package com.example.mvvmsecond.ViewModels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmsecond.DataModels.UpLoadProductDataModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FetchProductDataViewModel: ViewModel() {
    val product = MutableLiveData<ArrayList<UpLoadProductDataModel>>()
    val db= FirebaseDatabase.getInstance()


    fun fetchData(context: Context){
        db.getReference("mvvmProducts").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<UpLoadProductDataModel>()
                for (data in snapshot.children){
                    val item = data.getValue(UpLoadProductDataModel::class.java)
                    item.let { list.add(item!!) }
                    product.value = list
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Fetch Data Failed", Toast.LENGTH_SHORT).show()

            }

            })
    }
}