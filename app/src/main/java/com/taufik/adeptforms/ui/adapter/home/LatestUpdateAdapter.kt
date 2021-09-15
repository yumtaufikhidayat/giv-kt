package com.taufik.adeptforms.ui.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.taufik.adeptforms.data.model.home.LatestUpdate
import com.taufik.adeptforms.databinding.ItemLatestUpdateBinding

class LatestUpdateAdapter(
    private val latestUpdate: List<LatestUpdate>
) : RecyclerView.Adapter<LatestUpdateAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ItemLatestUpdateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(latestUpdate[position])
    }

    override fun getItemCount(): Int = latestUpdate.size

    inner class MyViewHolder(private val binding: ItemLatestUpdateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(latestUpdate: LatestUpdate) {
            binding.apply {
                tvName.text = latestUpdate.name
                tvType.text = latestUpdate.type
                tvStatus.text = latestUpdate.status
            }
        }
    }
}