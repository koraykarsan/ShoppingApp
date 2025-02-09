package com.koraykarsan.shoppingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartActivity : AppCompatActivity() {

    private val cartItems = CartSingleton.cartItems
    private lateinit var recyclerView: RecyclerView
    private lateinit var totalTextView: TextView
    private lateinit var checkoutButton: Button
    private lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        recyclerView = findViewById(R.id.cartRecyclerView)
        totalTextView = findViewById(R.id.totalTextView)
        checkoutButton = findViewById(R.id.checkoutButton)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val receivedNames = intent.getStringArrayListExtra("cartNames") ?: arrayListOf()
        val receivedPrices = intent.getStringArrayListExtra("cartPrices") ?: arrayListOf()
        val receivedImages = intent.getIntegerArrayListExtra("cartImages") ?: arrayListOf()

        val itemCount = minOf(receivedNames.size, receivedPrices.size, receivedImages.size)

        adapter = CartAdapter(cartItems,
            onRemoveClicked = { item ->
                cartItems.remove(item)
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "${item.name} removed from cart", Toast.LENGTH_SHORT).show()
                calculateTotal()
            },
            onQuantityChanged = {
                calculateTotal()
            }
        )

        recyclerView.adapter = adapter

        calculateTotal()

        checkoutButton.setOnClickListener {
            val totalPrice = cartItems.sumOf {
                val price = it.price.removePrefix("$").toIntOrNull() ?: 0
                price * it.quantity
            }

            val intent = Intent(this, PaymentActivity::class.java)
            intent.putExtra("TOTAL_PRICE", totalPrice)
            startActivity(intent)
        }
    }

    private fun calculateTotal() {
        val total = cartItems.sumOf {
            val price = it.price.removePrefix("$").toIntOrNull() ?: 0
            price * it.quantity
        }
        totalTextView.text = "Total: $$total"
    }
}
