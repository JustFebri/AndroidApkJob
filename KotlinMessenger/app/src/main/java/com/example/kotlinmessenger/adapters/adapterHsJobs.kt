package com.example.kotlinmessenger.jobs

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmessenger.R
import com.example.kotlinmessenger.adapters.adapterJobs
import com.example.kotlinmessenger.changeFragment
import com.example.kotlinmessenger.DetailsPage.*
import com.example.kotlinmessenger.listjobs
import com.example.kotlinmessenger.models.User
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class adapterHsJobs (var parentFragmentManager : FragmentManager,
                               val arrayAdapter: ArrayList<jobItem>, val isMine : Boolean) : RecyclerView.Adapter<adapterHsJobs.ListViewHolder>()
{
    inner class  ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _title : TextView = itemView.findViewById(R.id.tvt)
        var _description : TextView = itemView.findViewById(R.id.tvd)
        var _employee : TextView = itemView.findViewById(R.id.history_employee)
        var _employee_rating : TextView = itemView.findViewById(R.id.employee_rating)
        var _finish_time : TextView = itemView.findViewById(R.id.history_finish_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.rcv_hs, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayAdapter.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var arr = arrayAdapter[position]

        val mDatabase = FirebaseDatabase.getInstance().getReference()
        mDatabase.child("users").child(arr.worker).get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            val value = it.getValue<User>()
            holder._title.setText(arr.title)
            holder._description.setText(arr.description)
            if (value != null) {
                holder._employee.setText(value.username)
            }
            val db = Firebase.firestore
            db.collection("dbRating")
                .whereEqualTo("jobId", arr.id)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        holder._employee_rating.text = document.get("rating").toString()
                        holder._finish_time.text = document.get("timeFinished").toString()
//                    Log.w(ContentValues.TAG, document.get("id").toString())
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(ContentValues.TAG, "Error getting documents.", exception)
                }
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }
}