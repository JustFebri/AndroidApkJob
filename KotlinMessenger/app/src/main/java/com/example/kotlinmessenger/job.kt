package com.example.kotlinmessenger

import android.app.DatePickerDialog
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmessenger.jobs.jobItem
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [job.newInstance] factory method to
 * create an instance of this fragment.
 */

val listjobs = arrayListOf<jobItem>()
class job : Fragment() {
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
        return inflater.inflate(R.layout.fragment_job, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


    }

//    private fun getData(recyclerview:RecyclerView, filter:String = "null"){
//        var pemasukan : Int = 0
//        var pengeluaran : Int = 0
//        db.collection("dbLogs")
//            .get()
//            .addOnSuccessListener { result ->
//                listjobs.clear()
//                if(filter == "null")
//                {
//                    for (document in result) {
//                        listjobs.add(log(document.get("name").toString(), document.get("date").toString(),
//                            document.get("amount").toString(), document.get("category").toString(), document.get("type").toString(), document.get("id").toString()))
//                        if(document.get("type").toString() == "false")
//                        {
//                            pengeluaran += document.get("amount").toString().toInt()
//                        }
//                        else{
//                            pemasukan += document.get("amount").toString().toInt()
//                        }
//                    }
//                    recyclerview.layoutManager = LinearLayoutManager(view?.context)
//                    recyclerview.adapter = adapterlog()
//
//                    if (val_pengeluaran != null) {
//                        val_pengeluaran.text = pengeluaran.toString()
//                    }
//                    if (val_pemasukan != null) {
//                        val_pemasukan.text = pemasukan.toString()
//                    }
//                }
//                else{
//                    for (document in result) {
//                        if(document.get("date") == filter)
//                        {
//                            listLogs.add(log(document.get("name").toString(), document.get("date").toString(),
//                                document.get("amount").toString(), document.get("category").toString(), document.get("type").toString(), document.get("id").toString()))
//                            if(document.get("type").toString() == "false")
//                            {
//                                pengeluaran += document.get("amount").toString().toInt()
//                            }
//                            else{
//                                pemasukan += document.get("amount").toString().toInt()
//                            }
//                        }
//                    }
//                    recyclerview.layoutManager = LinearLayoutManager(view?.context)
//                    recyclerview.adapter = adapterlog()
//
//                    if (val_pengeluaran != null) {
//                        val_pengeluaran.text = pengeluaran.toString()
//                    }
//                    if (val_pemasukan != null) {
//                        val_pemasukan.text = pemasukan.toString()
//                    }
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.w(ContentValues.TAG, "Error getting documents.", exception)
//            }
//    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment job.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            job().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}