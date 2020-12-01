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
private const val TAG = "ProfileActivity"

class ProfileActivity : AppCompatActivity() {

    private lateinit var profileBioField: EditText
    private lateinit var profilePicture: ImageView
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        profileBioField = findViewById(R.id.profile_bio)

        val profilePicture = findViewById<ImageButton>(R.id.profile_picture)

        //listener for a bio change
        val bioWatcher = object : TextWatcher {

            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // This space intentionally left blank
            }

            /**
             * In onTextChanged(...), you call toString() on the CharSequence that is
             * the user's input.  This function returns a string, which you then use
             * to set the bio.
             */
            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
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
            }

            override fun afterTextChanged(sequence: Editable?) {
                // This one too
            }
        }

        profileBioField.addTextChangedListener(bioWatcher)
        profilePicture.setOnClickListener {
            Log.d("ProfileActivity", "Try to show login activity")
        val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)

            uploadImageToFirebaseStorage()
        }


    }
    private fun uploadImageToFirebaseStorage() {
        if (selectedPhotoUri == null) return
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d("ProfileActivity", "Sucessfully upload image: ${it.metadata?.path}")
            ref.downloadUrl.addOnSuccessListener {
                Log.d("ProfileActivity", "File Location: $it")

                saveUserToFirebaseDatabase(it.toString())
            }
            }
            .addOnFailureListener{
                // do some logging here
            }
    }
    private fun saveUserToFirebaseDatabase(profileImageUrl: String){
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User(uid, username.text.toString(), profileImageUrl)

        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("ProfileActivity", "Finally we saved the user to Firebase Database ")
            }
            .addOnFailureListener{
                // do some logging here
            }
    }
    var selectedPhotoUri: Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            Log.d("ProfileActivity", "Photo was selected")
            selectedPhotoUri = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)
            profile_picture.setImageBitmap(bitmap)
            profilePicture.alpha = 0f
            //val bitmapDrawable = BitmapDrawable(bitmap)
            //profile_picture.setBackgroundDrawable(bitmapDrawable)
        }
    }
    /**
     * A companion object allows you to access functions without having an instance of a class,
     * similar to static functions in Java.  Using a newIntent(...) function inside a companion
     * object like this for your activity subclasses will make it easy for other code to properly
     * configure their launching intents.
     */
    companion object {
        /**
         * This function allows you to create an Intent properly configured with the extras
         * CheatActivity will need.  The answerIsTrue argument, a Boolean, is put into the intent
         * with a private name using the EXTRA_ANSWER_IS_TRUE constant.
         */
        fun newIntent(packageContext: Context): Intent {
            Log.d(TAG, "Entered newIntent function in ProfileActivity")
            return Intent(packageContext, ProfileActivity::class.java)
        }
    }
    class User(val uid: String, val username: String, val profileImageUrl: String)
}

