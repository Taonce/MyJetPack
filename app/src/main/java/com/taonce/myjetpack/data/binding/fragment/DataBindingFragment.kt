package com.taonce.myjetpack.data.binding.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.taonce.myjetpack.R
import com.taonce.myjetpack.data.binding.UserBean
import com.taonce.myjetpack.databinding.FragmentDataBindingBinding

class DataBindingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Fragment中使用的是DataBindingUtil.inflate()来获取ViewDataBinding对象
        val binding = DataBindingUtil.inflate<FragmentDataBindingBinding>(
            inflater,
            R.layout.fragment_data_binding,
            container, false
        )
        binding.userBean = UserBean("Fragment", "")
        return binding.root
    }
}
