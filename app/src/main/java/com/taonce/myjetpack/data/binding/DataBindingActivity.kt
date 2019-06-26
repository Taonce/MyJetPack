package com.taonce.myjetpack.data.binding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.taonce.myjetpack.R
import com.taonce.myjetpack.data.binding.fragment.DataBindingFragment
import com.taonce.myjetpack.databinding.ActivityDataBindingBinding


class DataBindingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityDataBindingBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_data_binding
        )
        binding.userBean = UserBean("Activity", "Taonce")
        binding.otherName = " Android"
        supportFragmentManager.beginTransaction().add(R.id.frame_layout, DataBindingFragment()).commit()
    }
}
