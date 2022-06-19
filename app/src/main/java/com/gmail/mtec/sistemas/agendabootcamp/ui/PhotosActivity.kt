package com.gmail.mtec.sistemas.agendabootcamp.ui

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.gmail.mtec.sistemas.agendabootcamp.databinding.ActivityPhotosBinding

class PhotosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhotosBinding

    companion object {
        private val REQUEST_PERMISSION_GALLERY = 12
        private val REQUEST_PICK_PHOTO_GALLERY = 13

        private val REQUEST_PERMISSION_CAMERA = 21
        private val REQUEST_TAKE_PHOTO_CAMERA = 22
    }

    var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotosBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnPickGalleryPhoto.setOnClickListener {
            requestPermissionsAccess(REQUEST_PERMISSION_GALLERY)
        }

        binding.btnOpenCamera.setOnClickListener {
            requestPermissionsAccess(REQUEST_PERMISSION_CAMERA)
        }

    }


    private fun pickGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_PICK_PHOTO_GALLERY)
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE,"New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION,"Take picture from camera")

        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values)

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri)
        startActivityForResult(cameraIntent, REQUEST_TAKE_PHOTO_CAMERA)
    }

    private fun setImageView(data: Intent?) {
        binding.ivPhotoReceived.setImageURI(data?.data)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_PICK_PHOTO_GALLERY) {
                setImageView(data)
            }

            if (requestCode == REQUEST_TAKE_PHOTO_CAMERA){
                binding.ivPhotoReceived.setImageURI(imageUri)
            }

        }

    }


    fun requestPermissionsAccess(codePermission: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when (codePermission) {
                REQUEST_PERMISSION_GALLERY -> {
                    if (ActivityCompat.checkSelfPermission(
                            this,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {

                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            REQUEST_PERMISSION_GALLERY
                        )


                    } else {
                        pickGallery()

                    }
                }

                REQUEST_PERMISSION_CAMERA -> {
                    if (ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_DENIED ||
                        ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED
                    ) {
                        val permissions = arrayOf(
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )

                        ActivityCompat.requestPermissions(
                            this, permissions,
                            REQUEST_PERMISSION_CAMERA
                        )
                        //requestPermissions(permissions, REQUEST_PERMISSION_CAMERA)
                    } else {
                        openCamera()
                    }
                }

            }


        } else {
            when (codePermission) {
                REQUEST_PERMISSION_GALLERY -> {
                    pickGallery()
                }
                REQUEST_PERMISSION_CAMERA -> {
                    openCamera()
                }
            }

        }

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_PERMISSION_GALLERY -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickGallery()
                } else {
                    Toast.makeText(this, "Permission denid", Toast.LENGTH_SHORT).show()
                }
            }
            REQUEST_PERMISSION_CAMERA -> {
                    if (grantResults.isNotEmpty()
                        &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    ){
                        openCamera()
                    }
            }
        }


    }


}