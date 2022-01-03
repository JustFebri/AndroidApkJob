package com.example.kotlinmessenger

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.kotlinmessenger.jobs.jobItem
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.sql.Time
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [rating.newInstance] factory method to
 * create an instance of this fragment.
 */
class rating : Fragment() {
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
        return inflater.inflate(R.layout.fragment_rating, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val JobName = arguments?.getString("jobname").toString()
        val Worker = arguments?.getString("worker").toString()
        val Email = arguments?.getString("workeremail").toString()
        val JobId = arguments?.getString("jobId").toString()
        val WorkerId = arguments?.getString("workerId").toString()
        val EmployerId = arguments?.getString("recruiterId").toString()
        val nowTime: Calendar = Calendar.getInstance()
        val rateButton : Button = view.findViewById(R.id.rateButton)
        val id = JobName.hashCode().toString() + nowTime.timeInMillis.hashCode().toString()
        val ratingBar : RatingBar = view.findViewById(R.id.ratingBar)

        view.findViewById<TextView>(R.id.rating_job_name).text = JobName
        view.findViewById<TextView>(R.id.rating_worker_name).text = Worker
        view.findViewById<TextView>(R.id.rating_workerEmail).text = Email
        view.findViewById<TextView>(R.id.rating_finish_time).text = nowTime.time.toString()

        rateButton.setOnClickListener {
            db.collection("dbJobs").document(JobId)
                .update("status", "finished")
                .addOnSuccessListener {
//                    changeFragment(R.id.myframe, HomeActivity(), parentFragmentManager)
                    Log.d("Firebase", "accept data success")
                    changeFragment(R.id.ongoingframelayout, CompanyOngoing(), parentFragmentManager)
                }
                .addOnFailureListener{
                    Log.d("Firebase", it.message .toString())
                }
            val newData  = ratingItem(JobId, EmployerId, WorkerId, ratingBar.rating.toString(), nowTime.time.toString())
            db.collection("dbRating").document(id)
                .set(newData)
                .addOnSuccessListener {
//                    changeFragment(R.id.myframe, HomeActivity(), parentFragmentManager)
                    Log.d("Firebase", "Add data success")
                }
                .addOnFailureListener{
                    Log.d("Firebase", it.message .toString())
                }
        }


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment rating.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            rating().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}