package com.example.project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class CheckoutActivity : AppCompatActivity() {

    private lateinit var nameEditText: TextInputEditText
    private lateinit var cardNumberEditText: TextInputEditText
    private lateinit var cvvEditText: TextInputEditText
    private lateinit var addressEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.checkout)

        nameEditText = findViewById(R.id.editText)
        cardNumberEditText = findViewById(R.id.editText2)
        cvvEditText = findViewById(R.id.editText4)
        addressEditText = findViewById(R.id.editText5)


        val payButton: Button = findViewById(R.id.pay)
        val cancelButton: Button = findViewById(R.id.cancel)

        payButton.setOnClickListener {
            if (validateFields()) {
                Toast.makeText(this, "Your order is placed", Toast.LENGTH_SHORT).show()

            }
        }

        cancelButton.setOnClickListener {
            val intent = Intent(this, ProductActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun validateFields(): Boolean {
        // Validate Name
        val name = nameEditText.text.toString().trim()
        if (name.isEmpty()) {
            showToast("Please enter your name")
            return false
        } else if (!name.matches("[a-zA-Z ]+".toRegex())) {
            showToast("Name should contain only letters")
            return false
        }

        // Validate Card Number
        val cardNumber = cardNumberEditText.text.toString().trim()
        if (cardNumber.isEmpty()) {
            showToast("Please enter card number")
            return false
        }else if (!cardNumber.matches("[0-9]+".toRegex())) {
            showToast("Card Number should contain only numbers")
            return false
        }

        // Validate CVV
        val cvv = cvvEditText.text.toString().trim()
        if (cvv.isEmpty()) {
            showToast("Please enter CVV")
            return false
        } else if (!cvv.matches("[0-9]+".toRegex())) {
            showToast("CVV should contain only numbers")
            return false
        }

        val address = nameEditText.text.toString().trim()
        if (address.isEmpty()) {
            showToast("Please enter your name")
            return false
        } else if (!address.matches("[a-zA-Z ]+".toRegex())) {
            showToast("Name should contain only letters")
            return false
        }



        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
