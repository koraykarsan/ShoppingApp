package com.koraykarsan.shoppingapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(
    private val cartItems: List<Item>,
    private val onRemoveClicked: (Item) -> Unit,
    private val onQuantityChanged: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById(R.id.itemImage)
        val itemName: TextView = view.findViewById(R.id.itemName)
        val itemPrice: TextView = view.findViewById(R.id.itemPrice)
        val removeButton: Button = view.findViewById(R.id.removeButton)
        val buttonDecrease: Button = view.findViewById(R.id.buttonDecrease)
        val textViewQuantity: TextView = view.findViewById(R.id.textViewQuantity)
        val buttonIncrease: Button = view.findViewById(R.id.buttonIncrease)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]
        holder.itemImage.setImageResource(item.imageResId)
        holder.itemName.text = item.name
        holder.itemPrice.text = item.price
        holder.textViewQuantity.text = item.quantity.toString()
        holder.removeButton.setOnClickListener { onRemoveClicked(item) }

        holder.buttonIncrease.setOnClickListener {
            item.quantity++
            holder.textViewQuantity.text = item.quantity.toString()
            notifyItemChanged(position)
            onQuantityChanged()
        }

        holder.buttonDecrease.setOnClickListener {
            if (item.quantity > 1) {
                item.quantity--
                holder.textViewQuantity.text = item.quantity.toString()
                notifyItemChanged(position)
                onQuantityChanged()
            }
        }
    }

    override fun getItemCount(): Int = cartItems.size
}
