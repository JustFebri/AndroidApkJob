package com.example.kotlinmessenger

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmessenger.jobs.adapterJobs
import com.example.kotlinmessenger.jobs.applicantion
import com.example.kotlinmessenger.jobs.jobItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [myJobDetails.newInstance] factory method to
 * create an instance of this fragment.
 */
var applicantList = arrayListOf<applicantion>()
class myJobDetails : Fragment() {
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
        return inflater.inflate(R.layout.fragment_my_job_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerview : RecyclerView = view.findViewById((R.id.rvJobApplicant))
        getData(recyclerview)

    }

    private fun getData(recyclerview: RecyclerView){
        val uid = FirebaseAuth.getInstance().uid

        db.collection("dbApplications")
            .whereEqualTo("recruiterId", uid)
            .get()
            .addOnSuccessListener { result ->
                applicantList.clear()
                for (document in result) {
                    applicantList.add(applicantion(document.get("id").toString(),
                        document.get("applicantId").toString(),
                        document.get("recruiterId").toString(),
                        document.get("jobId").toString()))
                    Log.w("Testing", document.get("applicantId").toString())
                }

                recyclerview.layoutManager = LinearLayoutManager(view?.context)
                recyclerview.adapter = adapterApplicants(parentFragmentManager, applicantList)
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
         * @return A new instance of fragment myJobDetails.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            myJobDetails().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}