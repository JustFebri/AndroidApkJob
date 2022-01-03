package com.example.kotlinmessenger.adapters

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmessenger.R
import com.example.kotlinmessenger.changeFragment
import com.example.kotlinmessenger.job
import com.example.kotlinmessenger.jobs.applicantion
import com.example.kotlinmessenger.jobs.jobItem
import com.example.kotlinmessenger.listjobs
import com.example.kotlinmessenger.messages.ChatLogActivity
import com.example.kotlinmessenger.messages.LatestMessagesActivity
import com.example.kotlinmessenger.messages.UserItem
import com.example.kotlinmessenger.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.collections.ArrayList

class adapterApplicants (var parentFragmentManager : FragmentManager, val arrayAdapter: ArrayList<applicantion>)
    : RecyclerView.Adapter<adapterApplicants.ListViewHolder>()
{
    inner class  ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _applicantName : TextView = itemView.findViewById(R.id.applicant_name)
        var _applicantRating : TextView = itemView.findViewById(R.id.applicantrating)
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
            val db = Firebase.firestore
            db.collection("dbRating")
                .whereEqualTo("employeeId", applicant.applicantId)
                .get()
                .addOnSuccessListener { result ->
                    var rating : Float = 0.0F
                    var counter : Int = 0
                    for (document in result) {
                        rating += document.get("rating").toString().toFloat()
                        counter++
                    }
                    holder._applicantRating.text = (rating/counter).toString()
                }
                .addOnFailureListener { exception ->
                    Log.w(ContentValues.TAG, "Error getting documents.", exception)
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

    private fun fetchCurrentUser() {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {
                LatestMessagesActivity.currentUser = p0.getValue(User::class.java)
                Log.d("LatestMessages", "Current user ${LatestMessagesActivity.currentUser?.profileImageUrl}")
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }



    override fun getItemCount(): Int {
        return arrayAdapter.size
    }
}