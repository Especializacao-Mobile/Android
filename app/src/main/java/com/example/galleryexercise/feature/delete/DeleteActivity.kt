package com.example.galleryexercise.feature.delete

import android.content.ContentUris
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.galleryexercise.R
import com.example.galleryexercise.databinding.ActivityDeleteBinding
import com.example.galleryexercise.databinding.ActivityMainBinding
import com.example.galleryexercise.model.Image

class DeleteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeleteBinding
    private lateinit var image: Image

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent.getParcelableExtra<Image>(IMAGE)?.let {
            image = it
            setUpView()
        } ?: run {
            Toast.makeText(this, getString(R.string.unable_load), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpView(){
        binding.image.setImageURI(image.contentUri)
        binding.displayName.text = image.displayName

        binding.btnDelete.setOnClickListener {
            deleteImage(image.contentUri)
        }
    }

    private fun deleteImage(uri: Uri?) {
        uri?.let {
            try {
                contentResolver.delete(
                    uri,
                    "${MediaStore.Images.Media._ID} = ?",
                    arrayOf(ContentUris.parseId(uri).toString())
                )
                onBackPressed()
            } catch (securityException: SecurityException) {
                Toast.makeText(this, getString(R.string.unable_delete), Toast.LENGTH_SHORT).show()
            }
        } ?: run {
            Toast.makeText(this, getString(R.string.unable_delete), Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val IMAGE = "IMAGE"
    }

}