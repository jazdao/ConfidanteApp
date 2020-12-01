package com.bignerdranch.android.confidante

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.net.UrlQuerySanitizer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_profile.*
import java.util.*

//used in logging messages
private const val TAG = "EditBio"

class EditBio : AppCompatActivity() {

    private lateinit var profileBioField: EditText
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var editBioField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_bio)

        editBioField = findViewById(R.id.edit_bio)

        //listener for a bio change
//        val bioWatcher = object : TextWatcher {
//
//            override fun beforeTextChanged(
//                sequence: CharSequence?,
//                start: Int,
//                count: Int,
//                after: Int
//            ) {
//                // This space intentionally left blank
//            }

            /**
             * In onTextChanged(...), you call toString() on the CharSequence that is
             * the user's input.  This function returns a string, which you then use
             * to set the bio.
             */
//            override fun onTextChanged(
//                sequence: CharSequence?,
//                start: Int,
//                before: Int,
//                count: Int
//            ) {
                // Eventually, we edit the bio here
//                database = FirebaseDatabase.getInstance()
//                reference = database.reference.child("Users").child("lmdall")
//
//                //hardcoded vars temporarily
//                var name = "Lance Dall"
//                var bio = "This is a default bio"
//                var interestList = "This is a dummy placeholder sentence for what will end up being a list of interests"
//
//                val user = User(name, bio, interestList)
//
//                user.userBio = sequence.toString()
//
//                reference.setValue(user)
//            }
//
//            override fun afterTextChanged(sequence: Editable?) {
//                // This one too
//            }
//        }
//
//        editBioField.addTextChangedListener(bioWatcher)
    }
}

