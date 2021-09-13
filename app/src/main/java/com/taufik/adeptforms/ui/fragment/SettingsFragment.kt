package com.taufik.adeptforms.ui.fragment

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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

        backToHomeFragment()
    }

    private fun initFirebase() {
        auth = FirebaseAuth.getInstance()
    }

    private fun setSignOut() {
        binding.apply {
            cardSignOut.setOnClickListener {
                confirmSignOut()
            }
        }
    }

    private fun confirmSignOut() {

        AlertDialog.Builder(requireActivity()).also { builder ->
            builder.setTitle("Sign Out!")
                .setMessage("Are you sure you want to sign out?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes") { _: DialogInterface?, _: Int ->
                    auth.signOut()
                    Intent(requireActivity(), LoginActivity::class.java).also {
                        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(it)
                    }
                }
                .setCancelable(false)
                .create()
                .show()
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

    private fun backToHomeFragment() {
        binding.apply {
            cardBack.setOnClickListener {
                val action = SettingsFragmentDirections.actionNavSettingsToNavHome()
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}