package com.example.kotlinmessenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RatingBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class rate : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rate)
        val current_rating : Int
        val rate_button : Button = findViewById<Button>(R.id.rateButton)
        val ratingBar : RatingBar = findViewById<RatingBar>(R.id.ratingBar)
        val uid = FirebaseAuth.getInstance().uid
        val database = Firebase.database.getReference()
        if (uid != null) {
            database.child("users").child(uid).get().addOnSuccessListener {
                Log.i("firebase", "Got value ${it.value}")
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }
        }
        rate_button.setOnClickListener {
            val rateValue : Int = ratingBar.rating.toInt()

        }
    }
}