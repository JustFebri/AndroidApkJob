package com.example.kotlinmessenger

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmessenger.jobs.adapterJobs
import com.example.kotlinmessenger.jobs.applicantion
import com.example.kotlinmessenger.jobs.jobItem
import com.example.kotlinmessenger.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.FirebaseFirestore

class adapterApplicants (var parentFragmentManager : FragmentManager, val arrayAdapter: ArrayList<applicantion>)
    : RecyclerView.Adapter<adapterApplicants.ListViewHolder>()
{
    inner class  ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _applicantName : TextView = itemView.findViewById(R.id.applicant_name)
        var _applicantEmail : TextView = itemView.findViewById(R.id.applicant_email)
        val _detailsButton : ImageButton = itemView.findViewById(R.id.applicantDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_applicant, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val applicant = arrayAdapter[position]
        val applicantUID = applicant.applicantId
        Log.w("Testing", applicantUID)
        val mDatabase = FirebaseDatabase.getInstance().getReference()
        mDatabase.child("users").child(applicantUID).get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            val value = it.getValue<User>()
//            val usr : User = value as User

            if (value != null) {
                holder._applicantName.text = value.username.toString()
            }
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }



    override fun getItemCount(): Int {
        return arrayAdapter.size
    }
}