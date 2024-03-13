package com.example.project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance()

        // Find views by their IDs
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val signButton = findViewById<Button>(R.id.signupButton)

        // Set onClickListener for the login button
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            loginUser(email, password)// Call loginUser function with email and password
        }

        signButton.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)// Start SignupActivity when signup button is clicked
        }
    }
    // Function to handle user login
    private fun loginUser(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            showToast("Please enter both email and password.")
            return
        }

        if (!isValidEmail(email)) {
            showToast("Please enter a valid email address.")
            return
        }
        // Authenticate user with Firebase Authentication
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Login successful, redirect to product activity
                    val intent = Intent(this, ProductActivity::class.java)
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
    // Function to validate email format
    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
