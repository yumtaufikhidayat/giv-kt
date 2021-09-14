package com.taufik.adeptforms.ui.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.taufik.adeptforms.data.utils.LoadingDialog
import com.taufik.adeptforms.databinding.FragmentStorageBinding
import java.text.SimpleDateFormat
import java.util.*

class StorageFragment : Fragment() {

    private var _binding: FragmentStorageBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var imageUri : Uri

    companion object {
        const val REQUEST_CODE = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStorageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setInit()

        navigateToProfileFragment()

        selectImage()

        uploadImage()
    }

    private fun setInit() {
        loadingDialog = LoadingDialog(requireActivity())
        auth = FirebaseAuth.getInstance()
    }

    private fun navigateToProfileFragment() {
        binding.apply {
            cardBack.setOnClickListener {
                val actionToProfileFragment = StorageFragmentDirections.actionNavStorageToNavProfile()
                findNavController().navigate(actionToProfileFragment)
            }
        }
    }

    private fun selectImage() {
        binding.apply {
            btnSelectImage.setOnClickListener {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT

                startActivityForResult(intent, REQUEST_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            imageUri = data?.data!!
            binding.apply {
                imgProfileImage.setImageURI(imageUri)
            }
        }
    }

    private fun uploadImage() {
        binding.apply {
            btnUploadImage.setOnClickListener {
                loadingDialog.startLoadingDialog()
                val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val dateNow = Date()
                val fileName = formatter.format(dateNow)
                val storageReference = FirebaseStorage.getInstance().getReference("img/${auth.currentUser?.uid}/$fileName")

                if (this@StorageFragment::imageUri.isInitialized) {
                    storageReference.putFile(imageUri).addOnSuccessListener {
                        imgProfileImage.setImageURI(null)
                        Toast.makeText(
                            requireActivity(),
                            "Profile image successfully uploaded",
                            Toast.LENGTH_SHORT
                        ).show()

                        loadingDialog.dismissLoadingDialog()

                        val actionToProfile = StorageFragmentDirections.actionNavStorageToNavProfile()
                        findNavController().navigate(actionToProfile)

                    }.addOnFailureListener {
                        loadingDialog.dismissLoadingDialog()
                        Toast.makeText(
                            requireActivity(),
                            "Failed uploading profile image",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    loadingDialog.dismissLoadingDialog()
                    Toast.makeText(
                        requireActivity(),
                        "Please select image to upload",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}