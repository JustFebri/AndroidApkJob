package com.example.kotlinmessenger.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(val uid: String, val username: String, val country: String,
           val profileImageUrl: String, val employerRT : String, val employeeRT : String,
           val employerRC : String, val employeeRC : String): Parcelable {
    constructor() : this("", "", "", "", "0", "0","0","0")
}