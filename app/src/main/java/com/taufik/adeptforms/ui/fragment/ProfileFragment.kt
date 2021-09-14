package com.taufik.adeptforms.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.taufik.adeptforms.data.utils.DummyData
import com.taufik.adeptforms.databinding.FragmentProfileBinding
import com.taufik.adeptforms.ui.adapter.profile.ProfileAdapter

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var profileAdapter: ProfileAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backToHomeFragment()

        setProfileData()
    }

    private fun backToHomeFragment() {
        binding.apply {
            cardBack.setOnClickListener {
                val actionToHomeFragment = ProfileFragmentDirections.actionNavProfileToNavHome()
                findNavController().navigate(actionToHomeFragment)
            }
        }
    }

    private fun setProfileData() {
        profileAdapter = ProfileAdapter()
        binding.apply {
            rvProfile.layoutManager = LinearLayoutManager(requireActivity())
            rvProfile.setHasFixedSize(true)
            profileAdapter.setProfileData(DummyData.getAllProfiles())
            rvProfile.adapter = profileAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}