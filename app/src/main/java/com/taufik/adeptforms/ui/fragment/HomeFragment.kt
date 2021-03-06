package com.taufik.adeptforms.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.taufik.adeptforms.data.model.home.HomeAllCategory
import com.taufik.adeptforms.data.model.home.LatestUpdate
import com.taufik.adeptforms.data.model.users.Users
import com.taufik.adeptforms.data.utils.DummyData
import com.taufik.adeptforms.databinding.FragmentHomeBinding
import com.taufik.adeptforms.ui.adapter.home.LatestUpdateAdapter
import com.taufik.adeptforms.ui.adapter.home.MainCategoryAdapter
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var profileReference: StorageReference
    private lateinit var mainAdapter: MainCategoryAdapter
    private lateinit var latestUpdateAdapter: LatestUpdateAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigateToProfile()

        initFirebase()

        showProfileImage()

        retrieveDataFromDb()

        searchData()

        setMainRecyclerViewData()

        setLatestUpdateData(DummyData.getLatestUpdate())
    }

    private fun navigateToProfile() {
        binding.apply {
            cardProfile.setOnClickListener {
                val actionToProfile = HomeFragmentDirections.actionNavHomeToNavProfile()
                findNavController().navigate(actionToProfile)
            }
        }
    }

    private fun initFirebase() {
        auth = FirebaseAuth.getInstance()
        mainAdapter = MainCategoryAdapter(requireActivity(), DummyData.getHomeAllCategory())
    }

    private fun showProfileImage() {
        binding.apply {
            val storageReference = FirebaseStorage.getInstance().getReference("img/${auth.currentUser?.uid}/")
            profileReference = storageReference.child("profile.png")
            profileReference.downloadUrl.addOnSuccessListener {
                activity?.let { view ->
                    Glide.with(view)
                        .load(it)
                        .into(imgProfileImage)
                }
            }
        }
    }

    private fun retrieveDataFromDb() {
        binding.apply {
            firebaseUser = auth.currentUser!!
            val reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.uid)
            reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val users = snapshot.getValue(Users::class.java)

                    if (users != null) {

                        tvProfileName.text = users.fullName
                        tvCompanyName.text = users.companyName

                    } else {
                        Log.e(ProfileFragment.TAG, "onDataChange: $users")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(ProfileFragment.TAG, "onCancelled: ${error.message}")
                }
            })
        }
    }

    private fun searchData() {
        binding.apply {
            etSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    filterData(s.toString())
                }
            })
        }
    }

    private fun filterData(text: String) {
        val filteredList: ArrayList<HomeAllCategory> = ArrayList()

        for (item in DummyData.getHomeAllCategory()) {
            if (item.categoryTitle.lowercase(Locale.getDefault()).contains(text.lowercase(Locale.getDefault()))) {
                filteredList.add(item)
            }
        }

        mainAdapter.filterList(filteredList)
    }

    private fun setMainRecyclerViewData() {
        binding.apply {
            rvMainCategory.layoutManager = LinearLayoutManager(requireActivity())
            rvMainCategory.setHasFixedSize(true)
            rvMainCategory.adapter = mainAdapter
        }
    }

    private fun setLatestUpdateData(latestUpdate: List<LatestUpdate>) {
        latestUpdateAdapter = LatestUpdateAdapter(latestUpdate)
        binding.apply {
            rvLatestUpdate.layoutManager = LinearLayoutManager(requireActivity())
            rvLatestUpdate.setHasFixedSize(true)
            rvLatestUpdate.adapter = latestUpdateAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}