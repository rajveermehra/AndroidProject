package com.example.project
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference



        val createAccountButton: Button = findViewById(R.id.createAccount)
        val cancelButton: Button = findViewById(R.id.cancel)

        createAccountButton.setOnClickListener {
            val firstName = findViewById<EditText>(R.id.firstName).text.toString()
            val lastName = findViewById<EditText>(R.id.lastName).text.toString()
            val email = findViewById<EditText>(R.id.email).text.toString()
            val password = findViewById<EditText>(R.id.password).text.toString()

            createAccount(email, password, firstName, lastName)
        }


        cancelButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createAccount(email: String, password: String, firstName: String, lastName: String,) {
        if (email.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            showToast("Please fill in all fields.")
        } else if (!isValidEmail(email)) {
            showToast("Please enter a valid email address.")
        } else if (password.length < 6) {
            showToast("Password must be at least 6 characters long.")
        } else if (firstName.isBlank() || lastName.isBlank()) {
            showToast("Please enter a valid first and last name.")
        } else {  auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val userId = user?.uid
                    userId?.let {
                        val userMap = hashMapOf(
                            "firstName" to firstName,
                            "lastName" to lastName,
                            "email" to email,

                        )
                        database.child("users").child(userId).setValue(userMap)
                            .addOnCompleteListener { databaseTask ->
                                if (databaseTask.isSuccessful) {
                                    redirectToLogin()
                                } else {
                                    showToast("Failed to upload data.")
                                }
                            }
                    }
                } else {
                    showToast("Failed to create user account.")
                }
            }
    }

}
    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun redirectToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

    }
}