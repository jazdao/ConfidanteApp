package com.bignerdranch.android.confidante

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

//used in logging messages
private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    companion object {
        var currentUser: User? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        verifyUserIsLoggedIn()

        val chatButton = findViewById<ImageButton>(R.id.chat_button)
        val matchButton = findViewById<ImageButton>(R.id.match_button)
        val profileButton = findViewById<ImageButton>(R.id.profile_button)

        chatButton.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Chat Button Pressed", Toast.LENGTH_SHORT).show()
        }

        matchButton.setOnClickListener {
            val intent = Intent(this, MatchActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Match Button Pressed", Toast.LENGTH_SHORT).show()

        }

        profileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Profile Button Pressed", Toast.LENGTH_SHORT).show()

        }
    }

    private fun fetchCurrentUser() {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/Users/$uid")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                currentUser = p0.getValue(User::class.java)
                Log.d("Home", "Current user ${currentUser?.username}")
            }

            override fun onCancelled(p0: DatabaseError) {

            }

        })
    }

    private fun verifyUserIsLoggedIn() {
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null) {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        } else {
            fetchCurrentUser()
        }
    }
}
