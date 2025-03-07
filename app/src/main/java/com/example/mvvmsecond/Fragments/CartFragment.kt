package com.example.mvvmsecond.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmsecond.AllAdapters.CartAdapter
import com.example.mvvmsecond.AllDataModel.AddToCartDataModel
import com.example.mvvmsecond.AllViewModel.FetchCartDataViewModel
import com.example.mvvmsecond.databinding.FragmentAddToCartBinding

class CartFragment : Fragment() {
        lateinit var binding: FragmentAddToCartBinding
        lateinit var adapter: CartAdapter
        var list = ArrayList<AddToCartDataModel>()
        lateinit var viewModel: FetchCartDataViewModel
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

        adapter = CartAdapter(list)
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

}