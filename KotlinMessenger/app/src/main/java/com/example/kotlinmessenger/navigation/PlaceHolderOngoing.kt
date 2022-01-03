package com.example.kotlinmessenger.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlinmessenger.ApplicantOngoingFragment
import com.example.kotlinmessenger.CompanyOngoing
import com.example.kotlinmessenger.R
import com.example.kotlinmessenger.changeFragment
import kotlinx.android.synthetic.main.fragment_job_nav.*
import kotlinx.android.synthetic.main.fragment_job_nav.topjobNav
import kotlinx.android.synthetic.main.fragment_place_holder_ongoing.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PlaceHolderOngoing.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlaceHolderOngoing : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        return inflater.inflate(R.layout.fragment_place_holder_ongoing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        topOngBar.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.OnGoJob -> {
                    changeFragment(R.id.ongoingframelayout, CompanyOngoing(), parentFragmentManager)
                    true
                }

                R.id.ongoingwork  -> {
                    changeFragment(R.id.ongoingframelayout, ApplicantOngoingFragment(), parentFragmentManager)
                    true
                }
                else -> false
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
         * @return A new instance of fragment PlaceHolderOngoing.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PlaceHolderOngoing().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}