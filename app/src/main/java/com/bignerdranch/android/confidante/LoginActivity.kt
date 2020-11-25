package com.projectsandbox.confidentemessenger

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button.setOnClickListener {
            val email = registered_email_address.text.toString()
            val password = registered_password.text.toString()

            Log.d("MainActivity", "Email is: $email")
            Log.d("MainActivity", "Password is: $password")
        }

        register_account_text_view.setOnClickListener {
            Log.d("MainActivity", "Show Login Activity")

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}