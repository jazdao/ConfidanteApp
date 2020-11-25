package com.bignerdranch.android.confidante

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

//used in logging messages
private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val chatButton = findViewById<ImageButton>(R.id.chat_button)
        val matchButton = findViewById<ImageButton>(R.id.match_button)
        val profileButton = findViewById<ImageButton>(R.id.profile_button)

        chatButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        matchButton.setOnClickListener {
            val intent = Intent(this, MatchActivity::class.java)
            startActivity(intent)
        }

        profileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}