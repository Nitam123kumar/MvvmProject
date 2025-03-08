package com.example.mvvmsecond.AllAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmsecond.AllDataModel.AddToCartDataModel
import com.example.mvvmsecond.AllInterface.DeleteCartItem
import com.example.mvvmsecond.R

class CartAdapter(val list:ArrayList<AddToCartDataModel>,val deleteCartItem: DeleteCartItem): RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    class CartViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

        val image = itemView.findViewById<ImageView>(R.id.cartImageView)
        val tittle = itemView.findViewById<TextView>(R.id.cartTittleTV)
        val price = itemView.findViewById<TextView>(R.id.cartPriceTV)
        val description = itemView.findViewById<TextView>(R.id.descriptionCartTV)
        val delete = itemView.findViewById<TextView>(R.id.delete_cart_items)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.cart_item_layout,parent,false)
        return CartViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = list[position]
        holder.tittle.text = item.cartTittle
        holder.price.text = item.cartPrice
        holder.description.text = item.cartDescription
        Glide.with(holder.itemView.context).load(item.cartImage).into(holder.image)
        holder.delete.setOnClickListener {
           deleteCartItem.deleteItem(position,item)
        }

    }
}