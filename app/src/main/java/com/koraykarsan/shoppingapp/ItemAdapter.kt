package com.koraykarsan.shoppingapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(
    private val items: List<Item>,
    private val onAddToCartClicked: (Item) -> Unit,
    private val onBrowseClicked: (Item) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById(R.id.itemImage)
        val itemName: TextView = view.findViewById(R.id.itemName)
        val itemPrice: TextView = view.findViewById(R.id.itemPrice)
        val itemRating: TextView = view.findViewById(R.id.itemRating) // Added for ratings
        val browseButton: Button = view.findViewById(R.id.browseButton)
        val addToCartButton: Button = view.findViewById(R.id.addToCartButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]

        holder.itemImage.setImageResource(item.imageResId)
        holder.itemName.text = item.name
        holder.itemPrice.text = item.price
        holder.itemRating.text = "Rating: %.1f".format(item.averageRating) // Show average rating

        holder.browseButton.setOnClickListener {
            val intent = Intent(it.context, ItemDetailActivity::class.java).apply {
                putExtra("ITEM_NAME", item.name)
                putExtra("ITEM_PRICE", item.price)
                putExtra("ITEM_IMAGE", item.imageResId)
                putExtra("ITEM_DESCRIPTION", item.description)
            }
            it.context.startActivity(intent)
        }

        holder.addToCartButton.setOnClickListener { onAddToCartClicked(item) }
    }

    override fun getItemCount(): Int = items.size
}
