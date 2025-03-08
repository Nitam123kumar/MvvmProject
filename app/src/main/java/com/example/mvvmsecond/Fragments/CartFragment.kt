package com.example.mvvmsecond.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmsecond.AllAdapters.CartAdapter
import com.example.mvvmsecond.AllDataModel.AddToCartDataModel
import com.example.mvvmsecond.AllInterface.DeleteCartItem
import com.example.mvvmsecond.AllViewModel.FetchCartDataViewModel
import com.example.mvvmsecond.databinding.FragmentAddToCartBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CartFragment : Fragment() , DeleteCartItem {
        lateinit var binding: FragmentAddToCartBinding
        lateinit var adapter: CartAdapter
        var list = ArrayList<AddToCartDataModel>()
        lateinit var viewModel: FetchCartDataViewModel
        val db = FirebaseDatabase.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddToCartBinding.inflate(layoutInflater,container,false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[FetchCartDataViewModel::class.java]

        adapter = CartAdapter(list,this)
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRecyclerView.adapter = adapter
        viewModel.productList.observe(viewLifecycleOwner){ newList ->
            list.clear()
            list.addAll(newList)
            adapter.notifyDataSetChanged()
        }
        viewModel.fetchData()


        return view
    }

    override fun deleteItem(position: Int, data: AddToCartDataModel) {
        db.getReference("mvvmProducts").child("cart")
            .child(FirebaseAuth.getInstance().currentUser!!.uid).child(data.cartId.toString())
            .removeValue()
            .addOnSuccessListener {
                list.removeAt(position)
                adapter.notifyDataSetChanged()
                Toast.makeText(requireContext(), "Delete Success", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Delete Failed", Toast.LENGTH_SHORT).show()

            }
    }

}