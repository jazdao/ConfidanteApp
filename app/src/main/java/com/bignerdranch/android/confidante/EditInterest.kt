package com.bignerdranch.android.confidante

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

//used in logging messages
private const val TAG = "EditInterest"

class EditInterest : AppCompatActivity() {

    private lateinit var profileBioField: EditText
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var editInterestItem: EditText
    private lateinit var submitButton: Button
    private lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.interest_row)

        val uid = FirebaseAuth.getInstance().uid

        submitButton = findViewById(R.id.submit_button)
        cancelButton = findViewById(R.id.cancel_button)
        editInterestItem = findViewById(R.id.edit_interests)

        submitButton.setOnClickListener {
            if (editInterestItem.text.toString().isEmpty()) {
                Toast.makeText(this, "InterestField not Changed", Toast.LENGTH_SHORT).show()
            } else {
                FirebaseDatabase.getInstance().reference.child("Users/$uid/UserInterest").push().setValue(editInterestItem.text.toString())
            }
        }

        cancelButton.setOnClickListener {
            finish()
        }
    }
}
