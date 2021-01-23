package com.example.fruits.features.home

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fruits.R
import com.example.fruits.databinding.ActivityMainBinding
import com.example.fruits.databinding.ToolbarBinding
import com.example.fruits.features.addfruit.AddFruitActivity
import com.example.fruits.features.fruitdetails.FruitDetailsActivity
import com.example.fruits.features.fruitdetails.FruitDetailsActivity.Companion.FRUIT
import com.example.fruits.features.home.adapter.FruitAdapter
import com.example.fruits.model.Fruit
import com.example.fruits.model.mockedFruits

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbarBinding: ToolbarBinding
    private lateinit var fruits: ArrayList<Fruit>
    private lateinit var fruitAdapter: FruitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        toolbarBinding = ToolbarBinding.bind(binding.root)
        setContentView(binding.root)
        setSupportActionBar(toolbarBinding.toolbar)
        setUpRecyclerView()
        setUpFloatListener()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(MAIN_LIST_STATE, fruits)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        fruits.clear()
        fruits.addAll(savedInstanceState.getParcelableArrayList(MAIN_LIST_STATE) ?: mockedFruits.initialFruits)
        fruitAdapter.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == MAIN_ACTIVITY_ADD_FRUIT_REQUEST_CODE) {
                addFruitToList(data)
            } else if (requestCode == MAIN_ACTIVITY_DETAILS_REQUEST_CODE) {
                removeFruitFromList(data)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_filter -> {
            val builder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.custom_dialog, null)

            builder.apply {
                setView(view)
                setPositiveButton("Filter") { dialog, _ ->

                    dialog.dismiss()
                }
            }

            builder.create().show()
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setUpFloatListener() {
        binding.addFruit.setOnClickListener {
            startActivityForResult(
                Intent(this, AddFruitActivity::class.java),
                MAIN_ACTIVITY_ADD_FRUIT_REQUEST_CODE
            )
        }
    }

    private fun setUpRecyclerView() {
        fruits = ArrayList()
        fruits.addAll(mockedFruits.initialFruits)
        fruitAdapter = FruitAdapter(fruits, ::fruitDetails)
        binding.fruits.adapter = fruitAdapter
        binding.fruits.layoutManager = LinearLayoutManager(this)
    }

    private fun fruitDetails(fruit: Fruit) {
        val intent = Intent(this@MainActivity, FruitDetailsActivity::class.java)
        intent.putExtra(FRUIT, fruit)
        startActivityForResult(intent, MAIN_ACTIVITY_DETAILS_REQUEST_CODE)
    }

    private fun addFruitToList(data: Intent?) {
        data?.extras?.get(FRUIT_TO_ADD)?.let {
            fruits.add(it as Fruit)
            fruitAdapter.notifyItemInserted(fruits.lastIndex)
        } ?: run {
            showGenericErrorMessage()
        }
    }

    private fun removeFruitFromList(data: Intent?) {
        data?.extras?.get(FRUIT_TO_REMOVE)?.let {
            val positionToUpdate = fruits.indexOf(it)
            fruits.remove(it)
            fruitAdapter.notifyItemRemoved(positionToUpdate)
        } ?: run {
            showGenericErrorMessage()
        }
    }

    private fun showGenericErrorMessage() {
        Toast.makeText(
            this@MainActivity,
            getString(R.string.generic_error),
            Toast.LENGTH_LONG
        ).show()
    }

    companion object {
        const val MAIN_LIST_STATE = "save_list"
        const val MAIN_ACTIVITY_ADD_FRUIT_REQUEST_CODE = 0
        const val MAIN_ACTIVITY_DETAILS_REQUEST_CODE = 1
        const val FRUIT_TO_ADD = "fruit_to_add_to_list"
        const val FRUIT_TO_REMOVE = "fruit_to_remove"
    }
}