package com.example.mvvmsecond.Interface

import com.example.mvvmsecond.DataModels.AddToCartDataModel

interface DeleteCartItem {
    fun deleteItem(position: Int,data: AddToCartDataModel)
}