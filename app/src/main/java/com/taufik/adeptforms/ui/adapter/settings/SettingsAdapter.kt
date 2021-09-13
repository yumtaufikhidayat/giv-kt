package com.taufik.adeptforms.ui.adapter.settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.taufik.adeptforms.data.model.settings.Settings
import com.taufik.adeptforms.databinding.ItemSettingsBinding

class SettingsAdapter : RecyclerView.Adapter<SettingsAdapter.MyViewHolder>() {

    private var listSettings = ArrayList<Settings>()

    fun setSettingsData(settings: List<Settings>) {
        listSettings.clear()
        listSettings.addAll(settings)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ItemSettingsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val pos = listSettings[position]
        holder.bind(pos)
    }

    override fun getItemCount(): Int  = listSettings.size

    inner class MyViewHolder(private val binding: ItemSettingsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(settings: Settings) {
            binding.apply {
                imgIcon.setImageResource(settings.settingsImageIcon)
                tvDesc.text = settings.settingsDesc
                tvSettingsInfo.text = settings.settingsInfo

                when (adapterPosition) {
                    0 -> llSettingsInfo.visibility = View.VISIBLE
                    1 -> llSettingsInfo.visibility = View.VISIBLE
                    else -> llSettingsInfo.visibility = View.GONE
                }
            }
        }
    }
}