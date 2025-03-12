package com.example.mvvmsecond.DataModels

data class AddToCartDataModel(
    var cartId: String? = "",
    var cartTittle: String? = "",
    var cartDescription: String? = "",
    var cartImage: String? = "",
    var cartPrice: String? = ""
)
