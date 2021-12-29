package com.example.kotlinmessenger.jobs

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.example.kotlinmessenger.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class addJobs : AppCompatActivity() {
    val db = Firebase.firestore
    val arraySpinner = arrayListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_jobs)
        val addJobButton : Button = findViewById<Button>(R.id.addJobButton)
        val CategorySpinner : Spinner = findViewById<Spinner>(R.id.categoryspinner)

        addJobButton.setOnClickListener {

        }

       getCategories()

    }

    fun getCategories(){
        db.collection("dbCategory")
            .get()
            .addOnSuccessListener { result ->
                val s = findViewById<Spinner>(R.id.categoryspinner)
                if(this != null)
                {
                    arraySpinner.clear()
                    for (document in result) {
                        arraySpinner.add(document.get("category").toString())
                    }
                    val adapter = ArrayAdapter<String>(
                        this, android.R.layout.simple_spinner_item, arraySpinner
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    if (s != null) {
                        s.adapter = adapter
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }
}