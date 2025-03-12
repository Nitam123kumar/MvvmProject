package com.example.mvvmsecond.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmsecond.Adapters.MyOrderAdapter
import com.example.mvvmsecond.DataModels.MyOrderDataModel
import com.example.mvvmsecond.ViewModels.MyOrderGetViewModel
import com.example.mvvmsecond.databinding.FragmentMyOrderBinding


class MyOrderFragment : Fragment() {
    lateinit var binding: FragmentMyOrderBinding
    lateinit var adapter: MyOrderAdapter
    lateinit var viewModel: MyOrderGetViewModel
    var list = ArrayList<MyOrderDataModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }



    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMyOrderBinding.inflate(layoutInflater,container,false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[MyOrderGetViewModel::class.java]
        adapter = MyOrderAdapter(list)
        binding.myOrderRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.myOrderRecyclerView.adapter = adapter

        viewModel.fetchData()
        viewModel.productList.observe(viewLifecycleOwner) {
            list.clear()
            list.addAll(it)
            adapter.notifyDataSetChanged()
        }

        return view

    }

}