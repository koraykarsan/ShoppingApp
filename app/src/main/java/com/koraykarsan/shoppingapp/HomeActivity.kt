package com.koraykarsan.shoppingapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val cart = CartSingleton.cartItems

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        recyclerView = findViewById(R.id.itemRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 3)

        val items = listOf(
            Item("Desktop Computer", "$1200", R.drawable.desktop_computer, "A powerful desktop computer with high performance for all your needs."),
            Item("Laptop", "$900", R.drawable.laptop, "A lightweight and portable laptop, ideal for work and travel."),
            Item("Smartphone", "$800", R.drawable.smartphone, "A high-end smartphone with excellent camera quality and battery life."),
            Item("Tablet", "$400", R.drawable.tablet, "A versatile tablet for entertainment and productivity."),
            Item("Smartwatch", "$250", R.drawable.smartwatch, "A sleek smartwatch to keep you connected on the go."),
            Item("Headphones", "$150", R.drawable.headphones, "Noise-isolating headphones for immersive audio experience."),
            Item("Bluetooth Speaker", "$120", R.drawable.bluetooth_speaker, "Portable Bluetooth speaker with high-quality sound."),
            Item("Keyboard", "$80", R.drawable.keyboard, "A mechanical keyboard for precision typing."),
            Item("Mouse", "$50", R.drawable.mouse, "An ergonomic mouse for comfortable use."),
            Item("Gaming Chair", "$300", R.drawable.gaming_chair, "A comfortable gaming chair with adjustable features."),
            Item("Monitor", "$250", R.drawable.monitor, "A 27-inch monitor with stunning visuals."),
            Item("USB Flash Drive", "$20", R.drawable.usb_flash_drive, "A 64GB USB flash drive for your data storage needs."),
            Item("Printer", "$200", R.drawable.printer, "An all-in-one printer with scanning and copying capabilities."),
            Item("Webcam", "$80", R.drawable.webcam, "A high-definition webcam for video conferencing."),
            Item("Microphone", "$100", R.drawable.microphone, "A professional-grade microphone for clear audio recording."),
            Item("Graphics Card", "$600", R.drawable.graphics_card, "A powerful graphics card for gaming and video editing."),
            Item("CPU", "$300", R.drawable.cpu, "A fast processor for high-performance computing."),
            Item("RAM (16GB)", "$120", R.drawable.ram, "16GB of RAM for smooth multitasking."),
            Item("SSD (1TB)", "$150", R.drawable.ssd, "A 1TB SSD for fast data access and storage."),
            Item("Power Supply Unit", "$100", R.drawable.power_supply_unit, "A reliable power supply for your computer."),
            Item("Cooling Fan", "$50", R.drawable.cooling_fan, "A quiet cooling fan to keep your system cool."),
            Item("VR Headset", "$400", R.drawable.vr_headset, "A VR headset for immersive virtual reality experiences."),
            Item("Drone", "$500", R.drawable.drone, "A high-performance drone with a built-in camera."),
            Item("Smart Home Hub", "$200", R.drawable.smart_home_hub, "A smart home hub to control your connected devices."),
            Item("Gaming Console", "$400", R.drawable.gaming_console, "A popular gaming console with the latest titles."),
            Item("E-Reader", "$130", R.drawable.e_reader, "An e-reader with a glare-free screen for comfortable reading."),
            Item("Noise-Cancelling Headphones", "$300", R.drawable.noise_cancelling_headphones, "Premium noise-cancelling headphones for focused listening.")
        )

        val adapter = ItemAdapter(
            items,
            onBrowseClicked = { item ->
                val intent = Intent(this, ItemDetailActivity::class.java).apply {
                    putExtra("ITEM_NAME", item.name)
                    putExtra("ITEM_PRICE", item.price)
                    putExtra("ITEM_IMAGE", item.imageResId)
                    putExtra("ITEM_DESCRIPTION", item.description)
                }
                startActivity(intent)
            },
            onAddToCartClicked = { item ->
                cart.add(item)
                Toast.makeText(this, "${item.name} added to cart", Toast.LENGTH_SHORT).show()
            }
        )
        recyclerView.adapter = adapter

        findViewById<ImageButton>(R.id.buttonCart).setOnClickListener {
            if (cart.isEmpty()) {
                Toast.makeText(this, "Your cart is empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val cartNames = cart.map { it.name }
            val cartPrices = cart.map { it.price }
            val cartImages = cart.map { it.imageResId }

            val intent = Intent(this, CartActivity::class.java).apply {
                putStringArrayListExtra("cartNames", ArrayList(cartNames))
                putStringArrayListExtra("cartPrices", ArrayList(cartPrices))
                putIntegerArrayListExtra("cartImages", ArrayList(cartImages))
            }
            startActivity(intent)
        }

        findViewById<ImageButton>(R.id.buttonProfile).setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}
