package com.taufik.adeptforms.ui.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.taufik.adeptforms.R
import com.taufik.adeptforms.data.model.home.HomeChildCategory
import com.taufik.adeptforms.databinding.ItemChildHorizontalBinding
import com.taufik.adeptforms.ui.ReportsBottomSheet

class CategoryHorizontalAdapter(private val childCategory: List<HomeChildCategory>)
    : RecyclerView.Adapter<CategoryHorizontalAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ItemChildHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        
        val pos = childCategory[position]
        holder.bind(pos)

        holder.itemView.setOnClickListener {
            when (pos.itemId) {
                4 -> {
                    val reportBottomSheet = ReportsBottomSheet()
                    reportBottomSheet.setStyle(DialogFragment.STYLE_NORMAL, R.style.BaseBottomSheetMenu)
                    reportBottomSheet.show((holder.itemView.context as FragmentActivity).supportFragmentManager, "")
                }
            }
        }
    }

    override fun getItemCount(): Int = childCategory.size

    inner class MyViewHolder(private val binding: ItemChildHorizontalBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(childCategory: HomeChildCategory) {
            binding.apply {
                imgIcon.setImageResource(childCategory.icon)
                tvTitle.text = childCategory.title
            }
        }
    }
}