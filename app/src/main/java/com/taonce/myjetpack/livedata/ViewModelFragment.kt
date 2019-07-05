package com.taonce.myjetpack.livedata


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

import com.taonce.myjetpack.R
import kotlinx.android.synthetic.main.fragment_view_model.*
import kotlin.random.Random


class ViewModelFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(MyFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_model, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {
            viewModel.content.observe(it, Observer { content ->
                tv_fragment.text = content
            })
        }
        bt_fragment.setOnClickListener { viewModel.content.value = "Taonce ${Random.nextInt(1000)}" }
    }

}

class MyFragmentViewModel : ViewModel() {
    val content: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}
