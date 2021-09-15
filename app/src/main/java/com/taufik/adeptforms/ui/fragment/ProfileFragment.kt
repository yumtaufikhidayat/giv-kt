package com.taufik.adeptforms.ui.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.taufik.adeptforms.data.model.users.Users
import com.taufik.adeptforms.data.utils.DummyData
import com.taufik.adeptforms.databinding.FragmentProfileBinding
import com.taufik.adeptforms.ui.adapter.profile.ProfileAdapter
import java.util.*

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var profileAdapter: ProfileAdapter
    private lateinit var profileReference: StorageReference

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

        initFirebase()

        showProfileImage()

        retrieveDataFromDb()

        editUsername()

        editPassword()

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

    private fun initFirebase() {
        auth = FirebaseAuth.getInstance()
    }

    private fun showProfileImage() {
        binding.apply {
            val storageReference = FirebaseStorage.getInstance().getReference("img/${auth.currentUser?.uid}/")
            profileReference = storageReference.child("profile.png")
            profileReference.downloadUrl.addOnSuccessListener {
                Glide.with(requireActivity())
                    .load(it)
                    .into(imgProfileImage)
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
            firebaseUser = auth.currentUser!!
            val reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.uid)
            reference.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    val users = snapshot.getValue(Users::class.java)

                    if (users != null) {

                        tvProfileName.text = users.fullName
                        tvJobPosition.text = users.jobPosition
                        etEmail.setText(users.email)
                        etFullName.setText(users.fullName)
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

    private fun editUsername() {
        binding.apply {
            imgEditUsername.setOnClickListener {
                confirmUpdateUsername()
            }
        }
    }

    private fun confirmUpdateUsername() {
        binding.apply {
            AlertDialog.Builder(requireActivity()).apply {
                setTitle("Update Username!")
                setMessage("Are you sure you want to update username?")
                setNegativeButton("No", null)
                setPositiveButton("Yes") { _: DialogInterface?, _: Int ->
                    updateUsername(etUsername.text.toString())
                }
                setCancelable(false)
                create()
                show()
            }
        }
    }

    private fun updateUsername(username: String) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.uid)
        val mHashMap = HashMap<String, Any>()
        mHashMap["userName"] = username
        databaseReference.updateChildren(mHashMap)
    }

    private fun editPassword() {
        binding.apply {
            imgEditPassword.setOnClickListener {
                confirmUpdatePassword()
            }
        }
    }

    private fun confirmUpdatePassword() {
        binding.apply {
            AlertDialog.Builder(requireActivity()).apply {
                setTitle("Update Password!")
                setMessage("Are you sure you want to update password?")
                setNegativeButton("No", null)
                setPositiveButton("Yes") { _: DialogInterface?, _: Int ->
                    updatePassword(etPassword.text.toString())
                }
                setCancelable(false)
                create()
                show()
            }
        }
    }

    private fun updatePassword(password: String) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.uid)
        val mHashMap = HashMap<String, Any>()
        mHashMap["password"] = password
        databaseReference.updateChildren(mHashMap)
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