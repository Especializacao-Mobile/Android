package com.example.fruits.features.fruitdetails

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fruits.databinding.ActivityFruitDetailsBinding
import com.example.fruits.databinding.ToolbarBinding
import com.example.fruits.extensions.convertToBitmap
import com.example.fruits.features.home.MainActivity
import com.example.fruits.model.Fruit

class FruitDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFruitDetailsBinding
    private lateinit var toolbarBinding: ToolbarBinding
    private var fruit: Fruit? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFruitDetailsBinding.inflate(layoutInflater)
        toolbarBinding = ToolbarBinding.bind(binding.root)
        setContentView(binding.root)
        setUpView()
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.removeFruit.setOnClickListener {
            returnResult()
        }
    }

    private fun returnResult() {
        val returnIntent = Intent()
        returnIntent.putExtra(MainActivity.FRUIT_TO_REMOVE, fruit)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

    private fun setUpView() {
        setSupportActionBar(toolbarBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        fruit = intent.getParcelableExtra(FRUIT)
        fruit?.let { fruit ->
            binding.fruitImage.setImageBitmap(fruit.imageBase64?.convertToBitmap())
            supportActionBar?.title = fruit.name
            binding.fruitBenefits.text = fruit.benefits
        }
    }

    companion object {
        const val FRUIT = "fruit"
    }

}