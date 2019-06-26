package com.taonce.myjetpack.data.binding.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.taonce.myjetpack.R
import com.taonce.myjetpack.data.binding.UserBean
import com.taonce.myjetpack.databinding.ActivityListBinding
import com.taonce.myjetpack.databinding.ItemBinding

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityListBinding>(
            this,
            R.layout.activity_list
        )
        binding.adapter = ListAdapter(
            this, listOf(
                UserBean("one", ""),
                UserBean("two", ""),
                UserBean("three", "")
            )
        )
        binding.rcv.layoutManager = LinearLayoutManager(this)
    }
}

class ListAdapter(
    private val context: Context,
    val data: List<UserBean>
) : RecyclerView.Adapter<ListAdapter.Holder>() {
    lateinit var binding: ItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.item, parent, false
        )
        return Holder(binding.root)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        binding.userBean = data[position]
        // 立即改变数据
        binding.executePendingBindings()
    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)
}
