package com.koraykarsan.shoppingapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ItemDetailActivity : AppCompatActivity() {

    private lateinit var commentsAdapter: CommentsAdapter
    private val comments = mutableListOf<String>() // Initialize a mutable list for comments

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)

        // Retrieve the passed data
        val name = intent.getStringExtra("ITEM_NAME")
        val price = intent.getStringExtra("ITEM_PRICE")
        val imageResId = intent.getIntExtra("ITEM_IMAGE", 0)
        val description = intent.getStringExtra("ITEM_DESCRIPTION")
        val ratings = intent.getFloatArrayExtra("ITEM_RATINGS")?.toMutableList() ?: mutableListOf()

        // Initialize views
        findViewById<TextView>(R.id.itemName).text = name
        findViewById<TextView>(R.id.itemPrice).text = price
        findViewById<ImageView>(R.id.itemImage).setImageResource(imageResId)
        findViewById<TextView>(R.id.itemDescription).text = description

        val averageRatingText = findViewById<TextView>(R.id.itemAverageRating)
        val ratingBar = findViewById<RatingBar>(R.id.itemRatingBar)
        val submitRatingButton = findViewById<Button>(R.id.submitRatingButton)
        val commentInput = findViewById<EditText>(R.id.commentInput)
        val submitCommentButton = findViewById<Button>(R.id.submitCommentButton)
        val commentsRecyclerView = findViewById<RecyclerView>(R.id.commentsRecyclerView)

        // Calculate and display average rating
        val calculateAverageRating: () -> Unit = {
            val averageRating = if (ratings.isNotEmpty()) ratings.average().toFloat() else 0f
            averageRatingText.text = "Average Rating: %.1f".format(averageRating)
        }
        calculateAverageRating()

        // Handle rating submission
        submitRatingButton.setOnClickListener {
            val userRating = ratingBar.rating
            if (userRating > 0) {
                ratings.add(userRating) // Add the user's rating
                calculateAverageRating() // Update the average rating
                Toast.makeText(this, "Thank you for your rating!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please select a valid rating!", Toast.LENGTH_SHORT).show()
            }
        }

        // Set up comments RecyclerView
        commentsAdapter = CommentsAdapter(comments)
        commentsRecyclerView.layoutManager = LinearLayoutManager(this)
        commentsRecyclerView.adapter = commentsAdapter

        // Handle comment submission
        submitCommentButton.setOnClickListener {
            val comment = commentInput.text.toString()
            if (comment.isNotEmpty()) {
                comments.add(comment) // Add the new comment
                commentsAdapter.notifyDataSetChanged() // Refresh comments
                commentInput.text.clear() // Clear the input field
                Toast.makeText(this, "Comment added!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please write a comment!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
