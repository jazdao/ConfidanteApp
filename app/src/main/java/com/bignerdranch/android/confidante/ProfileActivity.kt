package com.bignerdranch.android.confidante

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_profile.*
import java.util.*

//used in logging messages
private const val TAG = "ProfileActivity"

class ProfileActivity : AppCompatActivity() {

    private lateinit var profileBioField: TextView
    private lateinit var profileInterestField: TextView
    //private lateinit var profilePicture: ImageView
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_profile)

        var editBio = findViewById<ImageButton>(R.id.edit_bio)
        var editInterestItem = findViewById<ImageButton>(R.id.edit_interests)

        editInterestItem.setOnClickListener {
            val intent = Intent(this, EditInterest::class.java)
            startActivity(intent)
            Toast.makeText(this, "Edit Interests Button", Toast.LENGTH_SHORT).show()
        }
        editBio.setOnClickListener {
            val intent = Intent(this, EditBio::class.java)
            startActivity(intent)
            Toast.makeText(this, "Edit Bio Button Pressed", Toast.LENGTH_SHORT).show()
        }
        profile_picture.setOnClickListener {
            Log.d("ProfileActivity", "Try to show login activity")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)

            uploadImageToFirebaseStorage()
        }

        val uid = FirebaseAuth.getInstance().uid

        profileBioField = findViewById(R.id.bio_textview)
        profileInterestField = findViewById(R.id.interest_textview)

        val profileBioFieldRef = FirebaseDatabase.getInstance().getReference("/Users/$uid/UserBio")
        val profileInterestFieldRef = FirebaseDatabase.getInstance().getReference("/Users/$uid/UserInterests")

        profileBioFieldRef.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                profileBioField.text = "This is my bio"
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        profileInterestFieldRef.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                profileInterestField.text = "These are my interests"
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

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
            profile_picture.alpha = 0f
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
