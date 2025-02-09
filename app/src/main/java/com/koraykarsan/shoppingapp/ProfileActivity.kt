package com.koraykarsan.shoppingapp

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.android.material.textfield.TextInputEditText
import android.widget.Button

class ProfileActivity : AppCompatActivity() {
    private lateinit var userEmailTextView: TextView
    private lateinit var nameInput: TextInputEditText
    private lateinit var phoneInput: TextInputEditText
    private lateinit var addressInput: TextInputEditText
    private lateinit var saveProfileButton: Button
    private lateinit var databaseReference: DatabaseReference
    private lateinit var currentUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        userEmailTextView = findViewById(R.id.userEmailTextView)
        nameInput = findViewById(R.id.nameInput)
        phoneInput = findViewById(R.id.phoneInput)
        addressInput = findViewById(R.id.addressInput)
        saveProfileButton = findViewById(R.id.saveProfileButton)

        val firebaseAuth = FirebaseAuth.getInstance()
        currentUser = firebaseAuth.currentUser!!

        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.uid)

        userEmailTextView.text = "Giriş yapıldı: ${currentUser.email ?: "Bilinmiyor"}"

        loadProfileData()

        saveProfileButton.setOnClickListener {
            saveProfileData()
        }
    }

    private fun loadProfileData() {
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                nameInput.setText(snapshot.child("name").getValue(String::class.java))
                phoneInput.setText(snapshot.child("phone").getValue(String::class.java))
                addressInput.setText(snapshot.child("address").getValue(String::class.java))
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ProfileActivity, "Veri yüklenemedi: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun saveProfileData() {
        val name = nameInput.text.toString().trim()
        val phone = phoneInput.text.toString().trim()
        val address = addressInput.text.toString().trim()

        val userMap = mapOf(
            "name" to name,
            "phone" to phone,
            "address" to address
        )

        databaseReference.setValue(userMap).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Profil başarıyla güncellendi", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Profil güncellenemedi: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
