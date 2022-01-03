package com.example.kotlinmessenger.jobs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmessenger.R
import com.example.kotlinmessenger.adapters.adapterJobs
import com.example.kotlinmessenger.changeFragment
import com.example.kotlinmessenger.DetailsPage.*
import com.google.firebase.firestore.FirebaseFirestore

class adapterHsJobs (var parentFragmentManager : FragmentManager,
                               val arrayAdapter: ArrayList<jobItem>, val isMine : Boolean) : RecyclerView.Adapter<adapterHsJobs.ListViewHolder>()
{
    inner class  ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _title : TextView = itemView.findViewById(R.id.tvt)
        var _description : TextView = itemView.findViewById(R.id.tvd)
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

        holder._title.setText(arr.title)
        holder._description.setText(arr.description)
    }
}