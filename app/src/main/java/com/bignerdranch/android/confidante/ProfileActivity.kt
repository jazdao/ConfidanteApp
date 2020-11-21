package com.bignerdranch.android.confidante

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

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
        profilePicture = findViewById(R.id.profile_picture)

        profilePicture.setImageResource(R.drawable.person);

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
                database = FirebaseDatabase.getInstance()
                reference = database.reference.child("Users").child("lmdall")

                //hardcoded vars temporarily
                var name = "Lance Dall"
                var bio = "This is a default bio"
                var interestList = "This is a dummy placeholder sentence for what will end up being a list of interests"

                var user = User(name, bio, interestList)

                user.userBio = sequence.toString()

                reference.setValue(user)
            }

            override fun afterTextChanged(sequence: Editable?) {
                // This one too
            }
        }

        profileBioField.addTextChangedListener(bioWatcher)

    }

    private fun setAnswerShownResult() {
        setResult(Activity.RESULT_OK)
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
}
