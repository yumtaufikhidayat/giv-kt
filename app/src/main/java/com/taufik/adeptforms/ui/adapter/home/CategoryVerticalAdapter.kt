package com.taufik.adeptforms.ui.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.taufik.adeptforms.data.model.home.HomeChildCategory
import com.taufik.adeptforms.databinding.ItemChildVerticalBinding

class CategoryVerticalAdapter(private val verticalCategory: List<HomeChildCategory>)
    : RecyclerView.Adapter<CategoryVerticalAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ItemChildVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(verticalCategory[position])
    }

    override fun getItemCount(): Int = verticalCategory.size

    inner class MyViewHolder(private val binding: ItemChildVerticalBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(verticalCategory: HomeChildCategory) {
            binding.apply {
                imgIcon.setImageResource(verticalCategory.icon)
                tvTitle.text = verticalCategory.title
                tvDesc.text = verticalCategory.description
            }
        }
    }
}