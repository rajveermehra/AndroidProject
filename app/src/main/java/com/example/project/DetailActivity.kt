package com.example.project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail)

        // Get the data passed from the previous activity
        val cakeName = intent.getStringExtra("cakeName")
        val cakeDescription = intent.getStringExtra("cakeDescription")
        val photoUrl = intent.getStringExtra("photo")
        val type = intent.getStringExtra("type")
        val price = intent.getStringExtra("price")

        // Find the views in the layout
        val nameTextView: TextView = findViewById(R.id.name)
        val descriptionTextView: TextView = findViewById(R.id.description)
        val cakeImageView: ImageView = findViewById(R.id.imageView)
        val backButton: Button = findViewById(R.id.backButton)
        val cartButton: Button = findViewById(R.id.cartButton)
        val typeTextView: TextView = findViewById(R.id.type)
        val priceTextView: TextView = findViewById(R.id.price)

        // Set the data to the views
        nameTextView.text = cakeName
        descriptionTextView.text = cakeDescription
        typeTextView.text = type
        priceTextView.text = price
        val storageRef: StorageReference = FirebaseStorage.getInstance().getReferenceFromUrl(photoUrl.toString())
        Glide.with(this).load(storageRef).into(cakeImageView)

        backButton.setOnClickListener {
            // Navigate back to the ProductActivity
            val intent = Intent(this, ProductActivity::class.java)
            startActivity(intent)
            finish()
        }


        cartButton.setOnClickListener {


                val intent = Intent(this, CartActivity::class.java)
                // Pass the data to the cartActivity
                intent.putExtra("cakeName", cakeName)
                intent.putExtra("photo", photoUrl)
                intent.putExtra("price", price)
                startActivity(intent)
            }

        }
    }

