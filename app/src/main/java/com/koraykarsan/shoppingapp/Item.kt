package com.koraykarsan.shoppingapp

data class Item(
    val name: String,
    val price: String,
    val imageResId: Int,
    val description: String,
    var quantity: Int = 1,
    val ratings: MutableList<Float> = mutableListOf()
)
{
    val averageRating: Float
        get() = if (ratings.isNotEmpty()) ratings.average().toFloat() else 0f
}