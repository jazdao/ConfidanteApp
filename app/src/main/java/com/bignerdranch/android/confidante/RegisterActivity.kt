package com.projectsandbox.confidentemessenger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_register.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register_button_registration.setOnClickListener {
            val email = email_registration.text.toString()
            val password = password_registration.text.toString()

            Log.d("MainActivity", "Email is: $email")
            Log.d("MainActivity", "Password is: $password")
        }

        existing_account_text_view.setOnClickListener {
            Log.d("MainActivity", "Show Login Activity")

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}