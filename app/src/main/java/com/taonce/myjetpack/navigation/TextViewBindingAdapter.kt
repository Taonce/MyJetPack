package com.taonce.myjetpack.navigation

import android.util.Log
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.Navigation
import com.taonce.myjetpack.R


@BindingAdapter("bind:onClick")
fun onClick(tv: LinearLayout, data: String) {
    tv.setOnClickListener {
        val controller = Navigation.findNavController(tv)
        Log.d("taonce", data)
        controller.navigate(R.id.action_BFragment_to_CFragment)
    }
}

