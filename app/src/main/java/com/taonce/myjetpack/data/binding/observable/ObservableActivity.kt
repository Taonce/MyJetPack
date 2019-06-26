package com.taonce.myjetpack.data.binding.observable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.taonce.myjetpack.R
import com.taonce.myjetpack.databinding.ActivityObservableBinding
import kotlin.random.Random

class ObservableActivity : AppCompatActivity() {
    lateinit var binding: ActivityObservableBinding
    private val bean = ObservableBean()
    private val title = ObservableField("ObservableActivity")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_observable)
        binding.activity = this
        binding.title = title
        bean.name = "taonce"
        bean.age = 20
        binding.bean = bean
    }

    fun changeContent() {
        title.set("ChangeTitle")
        bean.name = "ChangeName"
        bean.age = Random.nextInt(1000)
    }
}
