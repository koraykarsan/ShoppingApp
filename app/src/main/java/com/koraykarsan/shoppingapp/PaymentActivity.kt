package com.koraykarsan.shoppingapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import android.widget.TextView

class PaymentActivity : AppCompatActivity() {

    private lateinit var addressField: TextInputEditText
    private lateinit var cartIdField: TextInputEditText
    private lateinit var expiryDateField: TextInputEditText
    private lateinit var ccvField: TextInputEditText
    private lateinit var totalPriceText: TextView
    private lateinit var payButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        addressField = findViewById(R.id.addressField)
        cartIdField = findViewById(R.id.cartIdField)
        expiryDateField = findViewById(R.id.expiryDateField)
        ccvField = findViewById(R.id.ccvField)
        totalPriceText = findViewById(R.id.totalPriceText)
        payButton = findViewById(R.id.payButton)

        val totalPrice = intent.getIntExtra("TOTAL_PRICE", 0)
        totalPriceText.text = "Total Price: $$totalPrice"

        payButton.setOnClickListener {
            val address = addressField.text.toString().trim()
            val cartId = cartIdField.text.toString().trim()
            val expiryDate = expiryDateField.text.toString().trim()
            val ccv = ccvField.text.toString().trim()

            if (address.isEmpty() || cartId.isEmpty() || expiryDate.isEmpty() || ccv.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
