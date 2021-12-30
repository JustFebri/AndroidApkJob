package com.example.kotlinmessenger

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmessenger.jobs.adapterJobs
import com.example.kotlinmessenger.jobs.jobItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ApplicantOngoingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class ApplicantOngoingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_applicant_ongoing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val finish : Button = view.findViewById(R.id.finishJob)
        val uid = FirebaseAuth.getInstance().uid
        val jobtitle : TextView = view.findViewById(R.id.tvcNama)
        var cur = 0

        val db : FirebaseFirestore = FirebaseFirestore.getInstance()

        db.collection("dbJobs")
            .whereEqualTo("recruiterId", uid)
            .get()
            .addOnSuccessListener { result ->
                listjobs.clear()
                for (document in result) {
                    if(document.get("status").toString() == "ongoing")
                    {
                        listjobs.add(
                            jobItem(document.get("id").toString(),
                                document.get("title").toString(),
                                document.get("description").toString(),
                                document.get("recruiterId").toString(),
                                "ongoing",
                                document.get("worker").toString())
                        )
                    }
//                    Log.w(ContentValues.TAG, document.get("id").toString())
                }
                if(listjobs.size <= 0)
                {
                    jobtitle.text = "No Job Ongoing"
                    finish.visibility = View.INVISIBLE
                }
                else
                {
                    jobtitle.text = listjobs[cur].title
                    finish.visibility = View.VISIBLE
                }

            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ApplicantOngoingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ApplicantOngoingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}