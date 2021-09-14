package com.taufik.adeptforms.ui.adapter.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.taufik.adeptforms.data.model.profile.Profile
import com.taufik.adeptforms.databinding.ItemProfileBinding

class ProfileAdapter : RecyclerView.Adapter<ProfileAdapter.MyViewHolder>() {

    private val listProfile = ArrayList<Profile>()

    fun setProfileData(profile: List<Profile>) {
        listProfile.clear()
        listProfile.addAll(profile)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ItemProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val pos = listProfile[position]
        holder.bind(pos)
    }

    override fun getItemCount(): Int = listProfile.size

    inner class MyViewHolder(val binding: ItemProfileBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(profile: Profile) {
            binding.apply {
                tvProfileTitle.text = profile.profileTitle
                tvProfileTitleDesc.text = profile.profileDesc
            }
        }
    }
}