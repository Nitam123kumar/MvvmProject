package com.example.mvvmsecond.AllInterface

import com.example.mvvmsecond.AllDataModel.AddToCartDataModel

interface DeleteCartItem {
    fun deleteItem(position: Int,data: AddToCartDataModel)
}