package com.taonce.myjetpack.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.taonce.myjetpack.R
import kotlinx.android.synthetic.main.fragment_a.*

class AFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_a, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        buttona.setOnClickListener {
            // 动态设置startDestination
            // 用于splash -> home 之后将splash出栈
            val controller = Navigation.findNavController(it)
            val inflater = controller.navInflater
            val graph = inflater.inflate(R.navigation.my_navigation)
            graph.startDestination = R.id.BFragment
            controller.graph = graph
//            controller.navigate(R.id.action_AFragment_to_BFragment)
        }
    }
}
