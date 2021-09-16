package com.taufik.adeptforms.ui.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.taufik.adeptforms.data.model.home.Reports
import com.taufik.adeptforms.databinding.ItemBottomSheetBinding

class ReportsBottomSheetAdapter(
    private val reports: List<Reports>
) : RecyclerView.Adapter<ReportsBottomSheetAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ItemBottomSheetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val pos = reports[position]
        holder.bind(pos)
    }

    override fun getItemCount(): Int = reports.size

    inner class MyViewHolder(private val binding: ItemBottomSheetBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(reports: Reports) {
            binding.apply {
                tvJobsName.text = reports.jobsName
                tvClient.text = reports.client
                tvAreaAddress.text = reports.areaAddress
                tvClockInTime.text = String.format("%s \u2022 %s", reports.clockInDate, reports.clockInTime)
                tvClockOutTime.text = String.format("%s \u2022 %s", reports.clockOutDate, reports.clockOutTime)
            }
        }
    }
}