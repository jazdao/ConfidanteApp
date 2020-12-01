package com.bignerdranch.android.confidante

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

private const val TAG = "RegisterActivity"

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
        } else if (password.length < 6) {
            registerPasswordField.error = "Password is too short"
            false
        } else {
            registerPasswordField.error = null
            true
        }
    }

    private fun registerUser() {
        if (validateUsername() && validateEmail() && validatePassword()) {

            var uid: String
            var user: User

            Log.d(TAG, "Username is: $username")
            Log.d(TAG, "Email is: $email")
            Log.d(TAG, "Password is: $password")

            val auth = FirebaseAuth.getInstance()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        uid = task.result!!.user!!.uid
                        val userAuth = auth.currentUser

                        user = User(uid, username, email, password, "01")
                        reference.child(uid).setValue(user)

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        registerEmailField.error = "Must be a valid email not already in use"

                    }

                }

        }
    }
}