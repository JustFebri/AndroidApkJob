package com.example.kotlinmessenger

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmessenger.jobs.applicantion
import com.example.kotlinmessenger.jobs.jobItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [job_details.newInstance] factory method to
 * create an instance of this fragment.
 */
class job_details : Fragment() {
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
        return inflater.inflate(R.layout.fragment_job_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val apply : Button = view.findViewById(R.id.dt_apply)
        val dt_title : TextView = view.findViewById(R.id.dt_title)
        val dt_description : TextView = view.findViewById(R.id.dt_description)


        val nowTime: Calendar = Calendar.getInstance()
        val db : FirebaseFirestore = FirebaseFirestore.getInstance()
        val applicantID : String = FirebaseAuth.getInstance().uid.toString()
        val id = nowTime.timeInMillis.hashCode().toString() + applicantID + "AID"
        val recruiterId = arguments?.getString("recruiterId").toString()
        val jobtitle = arguments?.getString("title").toString()
        val jobdesc = arguments?.getString("description").toString()
        val jobId = arguments?.getString("jobId").toString()

        val newData  = applicantion(id, applicantID, recruiterId, jobId)
        apply.setOnClickListener {
            db.collection("dbApplications").document(id)
                .set(newData)
                .addOnSuccessListener {
//                    changeFragment(R.id.myframe, HomeActivity(), parentFragmentManager)
                    Log.d("Firebase", "Add data success")
                }
                .addOnFailureListener{
                    Log.d("Firebase", it.message .toString())
                }

            db.collection("dbHistory").document(id)
                .set(newData)
                .addOnSuccessListener {
//                    changeFragment(R.id.myframe, HomeActivity(), parentFragmentManager)
                    Log.d("Firebase", "Add data success")
                }
                .addOnFailureListener{
                    Log.d("Firebase", it.message .toString())
                }
        }
        dt_title.text = jobtitle
        dt_description.text = jobdesc

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment job_details.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            job_details().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}