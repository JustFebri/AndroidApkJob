package com.example.kotlinmessenger.DetailsPage

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.kotlinmessenger.CompanyOngoing
import com.example.kotlinmessenger.R
import com.example.kotlinmessenger.changeFragment
import com.example.kotlinmessenger.models.User
import com.example.kotlinmessenger.rating
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ongoing_job_details.newInstance] factory method to
 * create an instance of this fragment.
 */
class ongoing_job_details : Fragment() {
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
        return inflater.inflate(R.layout.fragment_ongoing_job_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val terminate : Button = view.findViewById(R.id.terminateContract)
        val jobId = arguments?.getString("jobId").toString()
        val email = FirebaseAuth.getInstance().currentUser?.email
        view.findViewById<TextView>(R.id.ongoing_job_job_title_details).text = arguments?.getString("jobname").toString()
        view.findViewById<TextView>(R.id.ongoing_job_employee_details).text = arguments?.getString("worker").toString()

        terminate.setOnClickListener {
            var mBundle = Bundle()
            mBundle.putString("jobname", arguments?.getString("jobname").toString())
            mBundle.putString("worker", arguments?.getString("worker").toString())
            mBundle.putString("workeremail", email)
            mBundle.putString("jobId", jobId)

            val rating = rating()
            rating.arguments = mBundle
            changeFragment(R.id.rvOngoingJob, rating, parentFragmentManager)

        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ongoing_job_details.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ongoing_job_details().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}