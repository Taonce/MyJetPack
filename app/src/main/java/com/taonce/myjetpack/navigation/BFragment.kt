package com.taonce.myjetpack.navigation


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import com.taonce.myjetpack.R
import kotlinx.android.synthetic.main.fragment_b.*

class BFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_b, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        buttonb.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_BFragment_to_CFragment)
//            Navigation.findNavController(it).navigateUp()
//            Navigation.findNavController(it).popBackStack(R.id.CFragment, false)
        }
    }
}
