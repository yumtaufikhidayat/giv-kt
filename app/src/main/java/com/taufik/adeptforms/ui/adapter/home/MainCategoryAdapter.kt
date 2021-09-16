package com.taufik.adeptforms.ui.adapter.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.taufik.adeptforms.data.model.home.HomeAllCategory
import com.taufik.adeptforms.data.model.home.HomeChildCategory
import com.taufik.adeptforms.databinding.ItemMainRecyclerviewBinding

class MainCategoryAdapter(
    private val context: Context,
    private var allCategory: List<HomeAllCategory>
) : RecyclerView.Adapter<MainCategoryAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ItemMainRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(allCategory[position])
    }

    override fun getItemCount(): Int = allCategory.size

    inner class MyViewHolder(private val binding: ItemMainRecyclerviewBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(allCategory: HomeAllCategory) {
            binding.apply {
                tvCategoryTitle.text = allCategory.categoryTitle
                when (adapterPosition) {
                    0 -> viewMain.visibility = View.GONE
                    else -> viewMain.visibility = View.VISIBLE
                }

                setHorizontalCategory(rvChildHorizontal, allCategory.horizontalCategory)
                setHorizontalCategory(rvChildHorizontal, allCategory.horizontalCategory)
                setVerticalCategory(rvChildVertical, allCategory.verticalCategory)
                setVerticalCategory(rvChildVertical, allCategory.verticalCategory)
            }
        }
    }

    private fun setHorizontalCategory(recyclerView: RecyclerView, horizontalCategory: List<HomeChildCategory>) {
        val horizontalAdapter = CategoryHorizontalAdapter(horizontalCategory)
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = horizontalAdapter
    }

    private fun setVerticalCategory(recyclerView: RecyclerView, verticalCategory: List<HomeChildCategory>) {
        val verticalAdapter = CategoryVerticalAdapter(verticalCategory)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = verticalAdapter
    }

    fun filterList(filteredList: List<HomeAllCategory>) {
        allCategory = filteredList
        notifyDataSetChanged()
    }
}