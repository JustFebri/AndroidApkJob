package com.example.kotlinmessenger.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmessenger.R
import com.example.kotlinmessenger.changeFragment
import com.example.kotlinmessenger.job
import com.example.kotlinmessenger.jobs.applicantion
import com.example.kotlinmessenger.models.User
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.collections.ArrayList

class adapterApplicants (var parentFragmentManager : FragmentManager, val arrayAdapter: ArrayList<applicantion>)
    : RecyclerView.Adapter<adapterApplicants.ListViewHolder>()
{
    inner class  ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _applicantName : TextView = itemView.findViewById(R.id.applicant_name)
        var _applicantEmail : TextView = itemView.findViewById(R.id.applicant_email)
        val acceptApplicant : ImageButton = itemView.findViewById(R.id.acceptapplicant)
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

            if (value != null) {
                holder._applicantName.text = value.username.toString()
            }

            holder.acceptApplicant.setOnClickListener {
                val db : FirebaseFirestore = FirebaseFirestore.getInstance()

                db.collection("dbJobs").document(applicant.jobId)
                    .update("worker", applicantUID)
                    .addOnSuccessListener {
//                    changeFragment(R.id.myframe, HomeActivity(), parentFragmentManager)
                        Log.d("Firebase", "accept data success")
                    }
                    .addOnFailureListener{
                        Log.d("Firebase", it.message .toString())
                    }
                db.collection("dbJobs").document(applicant.jobId)
                    .update("status", "ongoing")
                    .addOnSuccessListener {
//                    changeFragment(R.id.myframe, HomeActivity(), parentFragmentManager)
                        Log.d("Firebase", "accept data success")
                    }
                    .addOnFailureListener{
                        Log.d("Firebase", it.message .toString())
                    }
                changeFragment(R.id.myframe, job(), parentFragmentManager)
            }
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }

    }



    override fun getItemCount(): Int {
        return arrayAdapter.size
    }
}