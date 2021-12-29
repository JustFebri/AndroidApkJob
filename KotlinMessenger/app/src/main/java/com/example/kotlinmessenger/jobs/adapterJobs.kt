package com.example.kotlinmessenger.jobs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmessenger.R
import com.example.kotlinmessenger.listjobs
import com.google.firebase.firestore.FirebaseFirestore

val listJobs = arrayListOf<jobItem>()
class adapterJobs () : RecyclerView.Adapter<adapterJobs.ListViewHolder>()
{
    inner class  ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _title : TextView = itemView.findViewById(R.id.rv_jobtitle)
        var _description : TextView = itemView.findViewById(R.id.rv_jobdesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_jobs, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var job = listjobs[position]
        val db = FirebaseFirestore.getInstance()

        holder._title.setText(job.title)
        holder._description.setText(job.description)
    }

    override fun getItemCount(): Int {
        return listjobs.size
    }
}