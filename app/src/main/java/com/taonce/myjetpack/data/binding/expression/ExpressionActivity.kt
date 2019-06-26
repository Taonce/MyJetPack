package com.taonce.myjetpack.data.binding.expression

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.taonce.myjetpack.R
import com.taonce.myjetpack.data.binding.UserBean
import com.taonce.myjetpack.databinding.ActivityExpressionBinding

class ExpressionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityExpressionBinding>(
            this, R.layout.activity_expression
        )
        binding.ageLess20 = "年龄小于20就显示"
        binding.ageThan20 = "年龄大于20就显示"
        binding.age = 21
        binding.userBean = UserBean("Tao", "")
    }
}
