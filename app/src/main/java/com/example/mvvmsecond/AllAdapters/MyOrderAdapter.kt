package com.example.mvvmsecond.AllAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmsecond.AllDataModel.MyOrderDataModel
import com.example.mvvmsecond.R

class MyOrderAdapter(private val list: List<MyOrderDataModel>): RecyclerView.Adapter<MyOrderAdapter.MyOrderViewHolder>()  {
    class MyOrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image = itemView.findViewById<ImageView>(R.id.orderImageView)
        val tittle = itemView.findViewById<TextView>(R.id.orderTittle)
        val description = itemView.findViewById<TextView>(R.id.orderDescription)
        val price = itemView.findViewById<TextView>(R.id.orderPriceTV)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyOrderViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.my_order_layout,parent,false)
        return MyOrderViewHolder(layoutInflater)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyOrderViewHolder, position: Int) {
        val item = list[position]
       Glide.with(holder.itemView.context).load(item.orderImage).into(holder.image)
        holder.tittle.text = item.orderTittle
        holder.description.text = item.orderDescription
        holder.price.text = item.orderProductPrice
    }
}