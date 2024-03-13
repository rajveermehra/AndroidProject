package com.example.project
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class CartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart)
        // Get the data passed from the previous activity
        val cakeName = intent.getStringExtra("cakeName")
        val photoUrl = intent.getStringExtra("photo")
        val priceString = intent.getStringExtra("price")

        // Find the views in the layout
        val nameTextView: TextView = findViewById(R.id.name)
        val cakeImageView: ImageView = findViewById(R.id.imageView)
        val backButton: Button = findViewById(R.id.backButton)
        val checkoutButton: Button = findViewById(R.id.checkoutButton)
        val priceTextView: TextView = findViewById(R.id.price)
        val quantityText: TextView = findViewById(R.id.quantityText)
        // Set the data to the views
        nameTextView.text = cakeName
        priceTextView.text = priceString
        val storageRef: StorageReference =
            FirebaseStorage.getInstance().getReferenceFromUrl(photoUrl.toString())
        Glide.with(this).load(storageRef).into(cakeImageView)
       // Set a default quantity value
        var quantity = 1
        var pricePerItem = priceString;
        var price = pricePerItem?.substring(1)?.toIntOrNull() ?: 0

        // Listen for changes in the quantity EditText
        quantityText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // Calculate the total price based on quantity
                var quantity = s.toString().toIntOrNull() ?: 0
                var totalPrice = price * quantity
                priceTextView.text = totalPrice.toString()
            }
        })

        backButton.setOnClickListener {
            // Navigate back to the ProductActivity
            val intent = Intent(this, ProductActivity::class.java)
            startActivity(intent)
            finish()
        }

        checkoutButton.setOnClickListener {
            // Navigate back to the ProductActivity
            val intent = Intent(this, CheckoutActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}