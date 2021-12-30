package com.example.kotlinmessenger.jobs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmessenger.*
import com.google.firebase.firestore.FirebaseFirestore

class adapterJobs (var parentFragmentManager : FragmentManager,
                   val arrayAdapter: ArrayList<jobItem>, val isMine : Boolean) : RecyclerView.Adapter<adapterJobs.ListViewHolder>()
{
    inner class  ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _title : TextView = itemView.findViewById(R.id.rv_jobtitle)
        var _description : TextView = itemView.findViewById(R.id.rv_jobdesc)
        val _detailsButton : ImageButton = itemView.findViewById(R.id.detailsButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_jobs, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        if(!isMine) {
            browseJobs(position, holder)
        }
        else
        {
            myJobs(position, holder)
        }
    }

    fun browseJobs(position: Int, holder: ListViewHolder)
    {
        var job = arrayAdapter[position]
        val db = FirebaseFirestore.getInstance()

        holder._title.setText(job.title)
        holder._description.setText(job.description)

        holder._detailsButton.setOnClickListener {
            var mBundle = Bundle()
            mBundle.putString("recruiterId", job.recruiterId)
            mBundle.putString("title", job.title)
            mBundle.putString("description", job.description)

            val jobDetails = job_details()
            jobDetails.arguments = mBundle

            changeFragment(R.id.myframe, jobDetails, parentFragmentManager)
        }
    }

    fun myJobs(position: Int, holder: ListViewHolder)
    {
        var job = arrayAdapter[position]
        val db = FirebaseFirestore.getInstance()

        holder._title.setText(job.title)
        holder._description.setText(job.description)

        holder._detailsButton.setOnClickListener {
            var mBundle = Bundle()
            mBundle.putString("recruiterId", job.recruiterId)
            mBundle.putString("title", job.title)
            mBundle.putString("description", job.description)

            val jobDetails = myJobDetails()
            jobDetails.arguments = mBundle

            changeFragment(R.id.myframe, jobDetails, parentFragmentManager)
        }
    }



    override fun getItemCount(): Int {
        return arrayAdapter.size
    }
}