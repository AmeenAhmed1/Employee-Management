package com.github.ameen.emplyeemanagement.presentation.util

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.github.ameen.emplyeemanagement.R
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SelectImageUtil(private val activity: Fragment, private val response: MutableLiveData<Uri>) {

    fun selectImage() {
        if (ContextCompat.checkSelfPermission(
                activity.requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermission.launch(Manifest.permission.CAMERA)
        } else {
            showPictureDialog()
        }
    }

    private fun showPictureDialog() {
        MaterialAlertDialogBuilder(activity.requireContext())
            .setTitle(activity.resources.getString(R.string.select_image))
            .setItems(R.array.image_options) { dialog, which ->
                when (which) {
                    0 -> openGalleryForImage()
                    1 -> captureImageFullSize()
                }
            }
            .setNegativeButton(activity.resources.getString(R.string.cancel)) { dialog, which ->
                // Respond to neutral button press
                dialog.cancel()
            }
            .show()
    }

    private fun openGalleryForImage() {
        ImagePicker.with(activity)
            .crop(3f, 2f)
            .galleryOnly()//Crop image(Optional), Check Customization for more option
            .compress(1024)            //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            )    //Final image resolution will be less than 1080 x 1080(Optional)
            .createIntent {
                getResults.launch(it)
            }
    }

    private fun captureImageWithAspectRatio() {
        ImagePicker.with(activity)
            .crop(3f, 2f)
            .cameraOnly()
            .compress(1024)            //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            ).createIntent {
                getResults.launch(it)
            }
    }

    private fun captureImageFullSize() {
        ImagePicker.with(activity)
            .crop()
            .cameraOnly()
            .compress(1024)            //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            ).createIntent {
                getResults.launch(it)
            }
    }


    private val requestPermission =
        activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                showPictureDialog()
            }
        }


    private val getResults =
        activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = result.data?.data

                response.value = fileUri!!
//                file = File(fileUri!!.path)
//
//                binding.imgUserProfile.loadImage(fileUri)

            } else if (result.resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(
                    activity.requireActivity(),
                    ImagePicker.getError(result.data),
                    Toast.LENGTH_SHORT
                )
            } else {
//                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }

        }

}