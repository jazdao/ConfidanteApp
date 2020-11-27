package com.bignerdranch.android.confidante

import android.util.Log

//used in logging messages
private const val TAG = "User"

class User(
    var nameOfUser: String? = null,
    var userBio: String? = null,
    var userListOfInterests: String? = null /**For now, this is just a String.  Later, this will be something like List<Interest>*/
) {

    init {
        Log.d(TAG, "nameOfUser: $nameOfUser")
        Log.d(TAG, "userBio: $userBio")
        Log.d(TAG, "userListOfInterests: $userListOfInterests")
    }
}