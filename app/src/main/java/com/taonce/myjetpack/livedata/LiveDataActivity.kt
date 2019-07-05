package com.taonce.myjetpack.livedata

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.arch.core.util.Function
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import com.taonce.myjetpack.R
import com.taonce.myjetpack.data.binding.UserBean
import com.taonce.myjetpack.databinding.ActivityLiveDataBinding
import kotlinx.android.synthetic.main.activity_live_data.*
import kotlinx.android.synthetic.main.activity_live_data.view.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class LiveDataActivity : AppCompatActivity() {
    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityLiveDataBinding>(this, R.layout.activity_live_data)
    }
    private val myViewModel by lazy {
        ViewModelProviders.of(this).get(MyViewModel::class.java)
    }
    private val content = "Do not user LiveData"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activity = this
        binding.content = content
//        myViewModel.name.observe(this, Observer { name ->
//            tv_live_data.text = name
//        })

        // Transformation.map()可以将一个LiveData转换为另外一个LiveData对象
        // 返回的是LiveData对象，不可以setValue()和postValue()
//        Transformations.map(myViewModel.name) { name ->
//            UserBean("Taonce", name)
//        }.observe(this, Observer {
//            tv_live_data.text = "name is :${it.firstName}:${it.lastName}"
//        })

        Transformations.switchMap(myViewModel.name) { name ->
            getUser(name)
        }.observe(this, Observer {
            tv_live_data.text = "name is :${it.firstName}+${it.lastName}"
        })

        supportFragmentManager.beginTransaction().add(R.id.frame_layout, ViewModelFragment()).commit()

    }

    private fun getUser(name: String): LiveData<UserBean> {
        val userBean = MutableLiveData<UserBean>()
        userBean.value = UserBean(name, name)
        return userBean
    }

    fun changeContent() {
        // setValue() 可以在主线程中
        myViewModel.name.value = "Taonce " + Random.nextInt(1000)
        // 如果在子线程中，想要更新LiveDat需要使用postValue()
//        myViewModel.name.postValue("Taonce")

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("content", content)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        binding.content = savedInstanceState?.getString("content")
        super.onRestoreInstanceState(savedInstanceState)
    }
}

class MyViewModel : ViewModel() {
    val name: MutableLiveData<String> by lazy {
        MutableLiveData<String>("Taonce")
    }
}
