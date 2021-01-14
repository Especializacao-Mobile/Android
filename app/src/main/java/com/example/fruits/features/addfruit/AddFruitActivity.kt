package com.example.fruits.features.addfruit

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.fruits.R
import com.example.fruits.databinding.ActivityAddFruitBinding
import com.example.fruits.extensions.convertToBase64
import com.example.fruits.extensions.convertToBitmap
import com.example.fruits.features.home.MainActivity
import com.example.fruits.model.Fruit
import java.io.FileNotFoundException
import java.io.InputStream

class AddFruitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddFruitBinding
    private val phoneImagePickerType = "image/*"
    private val savedInstanceFruit = "fruit"
    private var fruit = Fruit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFruitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpChangeListener()
        setUpClickListeners()
    }

    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            try {
                setImage(data?.data)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                showGenericErrorMessage()
            }
        } else {
            pickImageMessage()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(savedInstanceFruit, fruit)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        loadData(savedInstanceState.getParcelable(savedInstanceFruit) ?: Fruit())
    }

    private fun setUpClickListeners(){
        binding.fruitImage.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = phoneImagePickerType
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG)
        }

        binding.addFruit.setOnClickListener {
            if (isFruitFieldsOk()) {
                val returnIntent = Intent()
                returnIntent.putExtra(MainActivity.FRUIT_TO_ADD, fruit)
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
        }
    }

    private fun setUpChangeListener(){
        binding.txFruitName.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isEmpty()) {
                binding.txFruitName.error = getString(R.string.error_empty_name)
            } else {
                fruit.name = text.toString()
                binding.txFruitName.error = null
            }
        }
        binding.txFruitBenefits.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isEmpty()) {
                binding.txFruitBenefits.error = getString(R.string.error_empty_benefits)
            } else {
                fruit.benefits = text.toString()
                binding.txFruitBenefits.error = null
            }
        }
    }

    private fun loadData(fruit: Fruit) {
        this.fruit = fruit
        fruit.imageBase64?.let {
            binding.fruitImage.setImageBitmap(fruit.imageBase64?.convertToBitmap())
        } ?: run {
            binding.fruitImage.setImageResource(android.R.drawable.ic_input_add)
        }
        binding.txFruitName.setText(fruit.name)
        binding.txFruitBenefits.setText(fruit.benefits)
    }

    private fun showGenericErrorMessage() {
        Toast.makeText(
            this@AddFruitActivity,
            getString(R.string.generic_error),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun setImage(imageUri: Uri?) {
        val imageStream: InputStream? =
            imageUri?.let { contentResolver.openInputStream(it) }
        val selectedImage = BitmapFactory.decodeStream(imageStream)
        binding.fruitImage.setImageResource(android.R.color.transparent)
        binding.fruitImage.setImageBitmap(selectedImage)
        fruit.imageBase64 = selectedImage.convertToBase64()
    }

    private fun pickImageMessage() {
        Toast.makeText(
            this@AddFruitActivity,
            getString(R.string.error_pick_image),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun isFruitFieldsOk(): Boolean {
        var result = true
        when {
            fruit.imageBase64 == null -> {
                pickImageMessage()
                result = false
            }
            binding.fruitName.editText?.text.toString().isEmpty() -> {
                binding.fruitName.error = "Empty Name!"
                result = false
            }
            binding.fruitBenefits.editText?.text.toString().isEmpty() -> {
                binding.fruitBenefits.error = "Empty Benefits!"
                result = false
            }
        }
        return result
    }

    companion object {
        const val RESULT_LOAD_IMG = 0
    }

}