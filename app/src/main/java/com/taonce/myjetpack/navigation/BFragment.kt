package com.taonce.myjetpack.navigation


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.taonce.myjetpack.R
import com.taonce.myjetpack.databinding.ItemFragmentBBinding
import kotlinx.android.synthetic.main.fragment_b.*
import kotlin.random.Random

class BFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_b, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        b_list.layoutManager = LinearLayoutManager(this.context)
        b_list.adapter = MyAdapter(this.context)
    }
}

class MyAdapter(private val context: Context?) : RecyclerView.Adapter<MyAdapter.MyHolder>() {
    var binding: ItemFragmentBBinding? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_fragment_b, parent, false)
        binding = DataBindingUtil.bind<ItemFragmentBBinding>(view)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        binding?.data = Random.nextInt(100).toString()
        binding?.executePendingBindings()
    }


    inner class MyHolder(val view: View) : RecyclerView.ViewHolder(view)
}
