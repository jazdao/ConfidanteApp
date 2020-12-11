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
private const val TAG = "EditBio"

class EditBio : AppCompatActivity() {

    private lateinit var profileBioField: EditText
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var editBioField: EditText
    private lateinit var submitButton: Button
    private lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.edit_bio)

        val uid = FirebaseAuth.getInstance().uid

        submitButton = findViewById(R.id.submit_button)
        cancelButton = findViewById(R.id.cancel_button)
        editBioField = findViewById(R.id.edit_bio)

        submitButton.setOnClickListener {
            if (editBioField.text.toString().isEmpty()) {
                Toast.makeText(this, "BioField not Changed", Toast.LENGTH_SHORT).show()
            } else {
                FirebaseDatabase.getInstance().reference.child("Users/$uid/UserBio").push().setValue(editBioField.text.toString())
            }
        }

        cancelButton.setOnClickListener {
            finish()
        }

    }
}
