package com.example.galleryexercise.feature.home

import android.Manifest
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.example.galleryexercise.R
import com.example.galleryexercise.adapter.GalleryAdapter
import com.example.galleryexercise.databinding.ActivityMainBinding
import com.example.galleryexercise.feature.delete.DeleteActivity
import com.example.galleryexercise.model.Image


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var images: ArrayList<Image>
    private lateinit var imagesAdapter: GalleryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestRead()
        setUpListeners()
    }

    private fun setUpListeners(){
        binding.btnRequestPermission.setOnClickListener {
            requestRead()
            requestWrite()
        }
    }

    override fun onResume() {
        requestRead()
        requestWrite()
        super.onResume()
    }

    private fun setUpRecyclerView() {
        switchVisibilities(true)
        images = getImages() as ArrayList<Image>
        imagesAdapter = GalleryAdapter(images){
            val intent = Intent(this, DeleteActivity::class.java)
            intent.putExtra(DeleteActivity.IMAGE, it)
            startActivity(intent)
        }
        binding.listGallery.adapter = imagesAdapter
        binding.listGallery.layoutManager = GridLayoutManager(this, 2)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(MAIN_LIST_STATE, images)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        images.addAll((savedInstanceState.getSerializable(MAIN_LIST_STATE) as? ArrayList<Image>)
                ?: ArrayList())
        imagesAdapter.notifyDataSetChanged()
    }
    
    companion object {
        const val MAIN_LIST_STATE = "MAIN_LIST_STATE"
        const val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1
        const val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1
    }

    private fun getImages(): List<Image> {
        val list = mutableListOf<Image>()
        val projection = arrayOf(
                MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DISPLAY_NAME
        )
        contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, null
        ).use {
            it?.let { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(
                        MediaStore.Images.ImageColumns._ID)
                val displayNameColumn = cursor.getColumnIndexOrThrow(
                        MediaStore.Images.ImageColumns.DISPLAY_NAME)
                while(cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val displayName = cursor.getString(displayNameColumn)
                    val contentUri = ContentUris.withAppendedId(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                    list.add(Image(id, displayName, contentUri))
                }
            }
        }
        return list
    }

    private fun switchVisibilities(galleryVisible: Boolean = false) {
        binding.listGallery.isVisible = galleryVisible
        binding.btnRequestPermission.isVisible = !galleryVisible
    }

    private fun requestRead() {
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
            )
        } else {
            setUpRecyclerView()
        }
    }

    private fun requestWrite() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
            )
        } else {
            setUpRecyclerView()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setUpRecyclerView()
            } else {
                Toast.makeText(this, getString(R.string.access_denied), Toast.LENGTH_SHORT).show()
                switchVisibilities()
            }
            return
        }
        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE) {
            Toast.makeText(this, getString(R.string.not_possible_to_delete) , Toast.LENGTH_SHORT).show()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    
}