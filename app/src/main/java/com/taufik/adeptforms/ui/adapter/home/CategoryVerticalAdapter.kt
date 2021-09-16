package com.taufik.adeptforms.ui.adapter.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.taufik.adeptforms.data.model.home.HomeChildCategory
import com.taufik.adeptforms.databinding.ItemChildVerticalBinding

class CategoryVerticalAdapter(private val childCategory: List<HomeChildCategory>)
    : RecyclerView.Adapter<CategoryVerticalAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ItemChildVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(childCategory[position])
    }

    override fun getItemCount(): Int = childCategory.size

    inner class MyViewHolder(private val binding: ItemChildVerticalBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(childCategory: HomeChildCategory) {
            binding.apply {
                imgIcon.setImageResource(childCategory.icon)
                tvTitle.text = childCategory.title
                tvDesc.text = childCategory.description

                when (childCategory.itemId) {
                    1 -> tvDesc.setTextColor(Color.parseColor("#CA3724"))
                }
            }
        }
    }
}