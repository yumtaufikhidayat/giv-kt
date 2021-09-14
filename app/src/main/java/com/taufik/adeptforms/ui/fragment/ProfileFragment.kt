package com.taufik.adeptforms.ui.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.taufik.adeptforms.data.utils.DummyData
import com.taufik.adeptforms.databinding.FragmentProfileBinding
import com.taufik.adeptforms.ui.adapter.profile.ProfileAdapter
import java.io.ByteArrayOutputStream

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var profileAdapter: ProfileAdapter
    private lateinit var imageUri: Uri

    companion object {
        const val REQUEST_CAMERA_CODE = 100
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

        setProfileImage()

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

    private fun setProfileImage() {
        binding.apply {
            cardEditImage.setOnClickListener {
                openCamera()
            }
        }
    }

    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            activity?.packageManager?.let {
                intent.resolveActivity(it).also {
                    startActivityForResult(intent, REQUEST_CAMERA_CODE)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CAMERA_CODE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            uploadImage(imageBitmap)
        }
    }

    private fun uploadImage(imageBitmap: Bitmap) {
        val byteArrayOutputString = ByteArrayOutputStream()
        val reference = FirebaseStorage.getInstance().reference.child(
            "img/${FirebaseAuth.getInstance().currentUser?.uid}"
        )

        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputString)
        val image = byteArrayOutputString.toByteArray()

        reference.putBytes(image)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    reference.downloadUrl.addOnCompleteListener { taskUri ->
                        taskUri.result?.let {
                            imageUri = it
                            binding.apply {
                                imgProfileImage.setImageBitmap(imageBitmap)
                            }
                        }
                    }
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