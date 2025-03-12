package com.example.mvvmsecond.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmsecond.Adapters.HomeProductAdapter
import com.example.mvvmsecond.DataModels.UpLoadProductDataModel
import com.example.mvvmsecond.ViewModels.FetchProductDataViewModel
import com.example.mvvmsecond.databinding.FragmentProductBinding


class ProductFragment : Fragment() {
    lateinit var binding: FragmentProductBinding
    lateinit var viewModel :FetchProductDataViewModel
    var list = ArrayList<UpLoadProductDataModel>()
    lateinit var adapter: HomeProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this).get(FetchProductDataViewModel::class.java)

        binding.homeProductRecyclerView.layoutManager= GridLayoutManager(requireContext(),2,LinearLayoutManager.VERTICAL,false)
        adapter = HomeProductAdapter(list)
        binding.homeProductRecyclerView.adapter = adapter


        viewModel.product.observe(viewLifecycleOwner){ newList ->
            list.clear()
            list.addAll(newList)
            adapter.notifyDataSetChanged()
        }

        viewModel.fetchData(requireContext())


        return view

    }

}