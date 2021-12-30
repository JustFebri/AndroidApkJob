package com.example.kotlinmessenger

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
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
 * Use the [addJob.newInstance] factory method to
 * create an instance of this fragment.
 */
class addJob : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val db = Firebase.firestore
    val arraySpinner = arrayListOf<String>()

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
        return inflater.inflate(R.layout.fragment_add_job, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val addJobButton: Button = view.findViewById<Button>(R.id.addJobButton)
//        val CategorySpinner : Spinner = view.findViewById<Spinner>(R.id.categoryspinner)
        addJobButton.setOnClickListener {
            val title : String = view.findViewById<TextView>(R.id.jobtitle).text.toString()
            val description : String = view.findViewById<TextView>(R.id.description).text.toString()
            val nowTime: Calendar = Calendar.getInstance()
            val id = nowTime.timeInMillis.hashCode().toString()
            val db : FirebaseFirestore = FirebaseFirestore.getInstance()
            val uid = FirebaseAuth.getInstance().uid.toString()
            val newData  = jobItem(id, title, description, uid, "pending", "")

            db.collection("dbJobs").document(id)
                .set(newData)
                .addOnSuccessListener {
//                    changeFragment(R.id.myframe, HomeActivity(), parentFragmentManager)
                    Log.d("Firebase", "Add data success")
                }
                .addOnFailureListener{
                    Log.d("Firebase", it.message .toString())
                }
        }
        getCategories()
    }

    fun getCategories() {
        db.collection("dbCategories")
            .get()
            .addOnSuccessListener { result ->
                val s = view?.findViewById<Spinner>(R.id.categoryspinner)
                if (this != null) {
                    arraySpinner.clear()
                    for (document in result) {
                        arraySpinner.add(document.get("category").toString())
                    }
                    val adapter = ArrayAdapter<String>(
                        this.requireContext(), android.R.layout.simple_spinner_item, arraySpinner
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    if (s != null) {
                        s.adapter = adapter
                    }
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
         * @return A new instance of fragment addJob.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            addJob().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}