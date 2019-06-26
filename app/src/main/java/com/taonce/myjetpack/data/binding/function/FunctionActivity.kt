package com.taonce.myjetpack.data.binding.function

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.taonce.myjetpack.R
import com.taonce.myjetpack.databinding.ActivityFunctionBinding

class FunctionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityFunctionBinding>(
            this, R.layout.activity_function
        )
        binding.activity = this
        binding.isToast = true
    }

    fun methodReference(view: View) {
        Toast.makeText(view.context, "MethodReference", Toast.LENGTH_SHORT).show()
    }

    /*
    * 不带参数的方法
    * */
    fun buttonClick() {
        Toast.makeText(this, "button click", Toast.LENGTH_SHORT).show()
    }

    /*
    * 带参数的方法
    * */
    fun toast(isToast: Boolean) {
        if (isToast) {
            Toast.makeText(this, "isToast is true", Toast.LENGTH_SHORT).show()
        }
    }
}
