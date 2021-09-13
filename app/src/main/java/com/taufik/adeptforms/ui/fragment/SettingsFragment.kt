package com.taufik.adeptforms.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.taufik.adeptforms.data.utils.DummyData
import com.taufik.adeptforms.databinding.FragmentSettingsBinding
import com.taufik.adeptforms.ui.activity.LoginActivity
import com.taufik.adeptforms.ui.adapter.settings.SettingsAdapter

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var settingsAdapter: SettingsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFirebase()

        setSignOut()

        setData()
    }

    private fun initFirebase() {
        auth = FirebaseAuth.getInstance()
    }

    private fun setSignOut() {
        binding.apply {
            cardSignOut.setOnClickListener {
                auth.signOut()
                Intent(requireActivity(), LoginActivity::class.java).also {
                    it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(it)
                }
                Toast.makeText(requireActivity(), "Successfully logout", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setData() {
        settingsAdapter = SettingsAdapter()
        binding.apply {
            rvSettings.layoutManager = LinearLayoutManager(requireActivity())
            rvSettings.setHasFixedSize(true)
            rvSettings.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
            settingsAdapter.setSettingsData(DummyData.getAllSettings())
            rvSettings.adapter = settingsAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}