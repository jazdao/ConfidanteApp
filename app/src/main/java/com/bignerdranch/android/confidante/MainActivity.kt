package com.bignerdranch.android.confidante

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

//used in logging messages
private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var profileButton: Button
    private lateinit var findButton: Button
    private lateinit var chatButton: Button
    private lateinit var profilePicture: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        profileButton = findViewById(R.id.profile_button)
        findButton = findViewById(R.id.find_button)
        chatButton = findViewById(R.id.chat_button)
        profilePicture = findViewById(R.id.profile_picture)

        profilePicture.setImageResource(R.drawable.person);

        profileButton.setOnClickListener {
            val intent = ProfileActivity.newIntent(this@MainActivity)
            Log.d(TAG, "Exited newIntent method in ProfileActivity")
            startActivity(intent)
        }
        findButton.setOnClickListener {
            Toast.makeText(this, R.string.find, Toast.LENGTH_SHORT).show()
        }
        chatButton.setOnClickListener {
            Toast.makeText(this, R.string.chat, Toast.LENGTH_SHORT).show()
        }
    }
}