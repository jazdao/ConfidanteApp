package com.bignerdranch.android.confidante

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*

private const val TAG = "LoginActivity"

class LoginActivity : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var loginUsernameField: EditText
    private lateinit var loginPasswordField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        database = FirebaseDatabase.getInstance()

        loginUsernameField = findViewById(R.id.registered_username)
        loginPasswordField = findViewById(R.id.registered_password)

        login_button.setOnClickListener {
            loginUser()
        }

        register_account_text_view.setOnClickListener {

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    private fun validateEmail(): Boolean {
        email = loginUsernameField.text.toString()

        return if(email.isEmpty()) {
            loginUsernameField.error = "Field cannot be empty"
            false
        } else {
            loginUsernameField.error = null
            true
        }
    }

    private fun validatePassword(): Boolean {
        password = loginPasswordField.text.toString()

        return if(password.isEmpty()) {
            loginPasswordField.error = "Field cannot be empty"
            false
        } else {
            loginPasswordField.error = null
            true
        }
    }

    /**method that checks if this user exists in our user database.
     * This will see if what our user entered for the 'email' and 'password' fields
     * exists and is valid within our database containing all users of our app.
     */
    private fun isUser(): Boolean {

        var flag: Boolean = false

        val usernameEntered = email.trim()
        val passwordEntered = password.trim()

        reference = database.getReference("Users")

        val checkUser = reference.orderByChild("username").equalTo(usernameEntered)

        checkUser.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {

                if (dataSnapshot.exists()) {    //if username entered exists in the database

                    loginUsernameField.error = null

                    val passwordFromDB =
                        dataSnapshot.child(usernameEntered).child("password").getValue(
                            String::class.java
                        )

                    if (passwordFromDB.equals(passwordEntered)) {
                        //validUser()
                        flag = true
                    } else {    //password entered for the given username is incorrect
                        loginPasswordField.error = "Incorrect Password"
                    }
                } else {    //username entered does not exist in the database
                    loginUsernameField.error = "Username is not linked to an existing account"
                }
            }

            override fun onCancelled(@NonNull databaseError: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return flag
    }

    private fun loginUser(){
        if (validateEmail() && validatePassword()) {

            //if (isUser()) {

            val auth = FirebaseAuth.getInstance()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        validUser()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                        // ...
                    }

                    // ...
                }
            //isUser()
        }
    }

    private fun validUser(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}