package com.bignerdranch.android.confidante

import android.os.Parcelable
import android.util.Log
import kotlinx.android.parcel.Parcelize

private const val TAG = "User"

@Parcelize
class User(
    var uid: String,
    var username: String,
    var email: String,
    var password: String,
    var profileImageUrl: String,
    var userBio: String? = null,
    var userListOfInterests: String? = null
    /**For now, this is just a String.  Later, this will be something like List<Interest>*/
): Parcelable {

    constructor() : this("", "", "", "", "")

    init {
        Log.d(TAG, "nameOfUser: $username")
        Log.d(TAG, "userBio: $userBio")
        Log.d(TAG, "userListOfInterests: $userListOfInterests")

    }
}
