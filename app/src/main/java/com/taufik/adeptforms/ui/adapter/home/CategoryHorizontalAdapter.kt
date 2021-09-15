package com.taufik.adeptforms.ui.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.taufik.adeptforms.data.model.home.HomeChildCategory
import com.taufik.adeptforms.databinding.ItemChildHorizontalBinding

class CategoryHorizontalAdapter(private val horizontalCategory: List<HomeChildCategory>)
    : RecyclerView.Adapter<CategoryHorizontalAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ItemChildHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(horizontalCategory[position])
    }

    override fun getItemCount(): Int = horizontalCategory.size

    inner class MyViewHolder(private val binding: ItemChildHorizontalBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(horizontalCategory: HomeChildCategory) {
            binding.apply {
                imgIcon.setImageResource(horizontalCategory.icon)
                tvTitle.text = horizontalCategory.title
            }
        }
    }
}