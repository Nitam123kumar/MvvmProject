package com.example.mvvmsecond.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmsecond.DataModels.UpLoadProductDataModel
import com.example.mvvmsecond.ProductDetailsActivity
import com.example.mvvmsecond.R

class HomeProductAdapter(val list:ArrayList<UpLoadProductDataModel>): RecyclerView.Adapter<HomeProductAdapter.HomeViewHolder>() {
    class HomeViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val image = itemView.findViewById<ImageView>(R.id.productImageView)
        val tittle = itemView.findViewById<TextView>(R.id.Tittle)
        val price = itemView.findViewById<TextView>(R.id.priceTV)
        val description = itemView.findViewById<TextView>(R.id.descriptionTV)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.home_page_item_layout,parent,false)
        return HomeViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = list[position]
        holder.tittle.text = item.productTittle
        holder.price.text = item.productPrice
        holder.description.text = item.productDescription
        Glide.with(holder.itemView.context).load(item.productImage).into(holder.image)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProductDetailsActivity::class.java)
            intent.putExtra("productTittle",item.productTittle)
            intent.putExtra("productPrice",item.productPrice)
            intent.putExtra("productDescription",item.productDescription)
            intent.putExtra("productImage",item.productImage)
            holder.itemView.context.startActivity(intent)



        }

    }
}