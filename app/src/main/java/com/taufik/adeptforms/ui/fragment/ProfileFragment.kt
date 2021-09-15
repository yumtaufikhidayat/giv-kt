package com.taufik.adeptforms.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.taufik.adeptforms.data.model.users.Users
import com.taufik.adeptforms.data.utils.DummyData
import com.taufik.adeptforms.databinding.FragmentProfileBinding
import com.taufik.adeptforms.ui.adapter.profile.ProfileAdapter

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var profileAdapter: ProfileAdapter

    companion object {
        const val TAG = "PROFILE_FRAGMENT"
    }

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

        retrieveDataFromDb()

        navigateToStorageFragment()

        setProfileAppIntegrations()
    }

    private fun backToHomeFragment() {
        binding.apply {
            cardBack.setOnClickListener {
                val actionToHomeFragment = ProfileFragmentDirections.actionNavProfileToNavHome()
                findNavController().navigate(actionToHomeFragment)
            }
        }
    }

    private fun navigateToStorageFragment() {
        binding.apply {
            cardEditImage.setOnClickListener {
                val actionToStorage = ProfileFragmentDirections.actionNavProfileToNavStorage()
                findNavController().navigate(actionToStorage)
            }
        }
    }

    private fun retrieveDataFromDb() {
        binding.apply {
            auth = FirebaseAuth.getInstance()
            firebaseUser = auth.currentUser!!
            val reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.uid)
            reference.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    val users = snapshot.getValue(Users::class.java)

                    if (users != null) {

                        tvProfileName.text = users.fullName
                        tvJobPosition.text = users.jobPosition
                        etEmail.text = users.email
                        etFullName.text = users.fullName
                        etUsername.setText(users.userName)
                        etPassword.setText(users.password)

                    } else {
                        Log.e(TAG, "onDataChange: $users")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, "onCancelled: ${error.message}")
                }
            })
        }
    }

    private fun setProfileAppIntegrations() {
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