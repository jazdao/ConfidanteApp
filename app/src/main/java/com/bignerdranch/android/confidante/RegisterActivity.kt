package com.bignerdranch.android.confidante

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var username: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var registerUsernameField: EditText
    private lateinit var registerEmailField: EditText
    private lateinit var registerPasswordField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerUsernameField = findViewById(R.id.username_registration)
        registerEmailField = findViewById(R.id.email_registration)
        registerPasswordField = findViewById(R.id.password_registration)

        database = FirebaseDatabase.getInstance()
        reference = database.getReference("Users")

        existing_account_text_view.setOnClickListener {
            Log.d("MainActivity", "Show Login Activity")

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        register_button_registration.setOnClickListener {
            registerUser()

        }
    }

    private fun validateUsername(): Boolean {
        username = registerUsernameField.text.toString()

        return if(username.isEmpty()) {
            registerUsernameField.error = "Field cannot be empty"
            false
        } else if (username.length >= 20) {
            registerUsernameField.error = "Username is too long"
            false
        } else {
            registerUsernameField.error = null
            true
        }
    }

    private fun validateEmail(): Boolean {
        email = registerEmailField.text.toString()

        return if(email.isEmpty()) {
            registerEmailField.error = "Field cannot be empty"
            false
        } else {
            registerEmailField.error = null
            true
        }
    }

    private fun validatePassword(): Boolean {
        password = registerPasswordField.text.toString()

        return if(password.isEmpty()) {
            registerPasswordField.error = "Field cannot be empty"
            false
        } else {
            registerPasswordField.error = null
            true
        }
    }

    private fun registerUser() {
        if (validateUsername() && validateEmail() && validatePassword()) {

            val user = User(username, email, password)

            Log.d("MainActivity", "Username is: $username")
            Log.d("MainActivity", "Email is: $email")
            Log.d("MainActivity", "Password is: $password")

            reference.child(username).setValue(user)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}