package com.example.shoppingliststartcodekotlin

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingliststartcodekotlin.adapters.ProductAdapter
import com.example.shoppingliststartcodekotlin.data.Repository
import com.example.shoppingliststartcodekotlin.models.Product
import com.example.shoppingliststartcodekotlin.databinding.ActivityMainBinding
import com.google.firebase.firestore.R

class MainActivity : AppCompatActivity() {

    //you need to have an Adapter for the products
   lateinit var adapter: ProductAdapter
   lateinit var binding : ActivityMainBinding
   lateinit var viewModel : MainViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.getData().observe(this, Observer {
            Log.d("Products","Found ${it.size} products")
            updateUI(it)
        })

        // biding and adding event listner
        binding.content.addProductBtn.setOnClickListener {
            val product = Product(
                name = binding.content.editProductName.text.toString(),
                quantity = binding.content.editQuantity.text.toString().toInt())
            Repository.addProduct(product)
            adapter.notifyDataSetChanged();
        }

        binding.content.sortNameBtn.setOnClickListener {
            Repository.products.sortBy { it.name }
            adapter.notifyDataSetChanged()
        }

        binding.content.sortQuantityBtn.setOnClickListener {
            Repository.products.sortByDescending { it.quantity }
            adapter.notifyDataSetChanged()
        }
    }

    fun updateUI(products : MutableList<Product>) {
        val layoutManager = LinearLayoutManager(this)

       //binding.productRecyclerView.layoutManager = layoutManager
        binding.content.productRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        adapter = ProductAdapter(products)

      /*connecting the recyclerview to the adapter  */
        binding.content.productRecyclerView.adapter = adapter

    }

    fun showDialog(v: View) {
        //showing our dialog.

        val dialog = MyDialogFragment(::yesBtnClicked, ::noBtnClick)
        //Here we show the dialog
        //The tag "MyFragement" is not important for us.
        dialog.show(supportFragmentManager, "myFragment")
    }

    //callback function from yes/no dialog - for yes choice
    private fun yesBtnClicked() {
        val toast = Toast.makeText(
            this,
            "yes button clicked", Toast.LENGTH_LONG
        )
        Repository.deleteAllProducts()
        adapter.notifyDataSetChanged();
        toast.show()

    }

    //callback function from yes/no dialog - for no choice
    private fun noBtnClick() {
        //Here we override the method and can now do something
        val toast = Toast.makeText(
            this,
            "no button clicked", Toast.LENGTH_LONG
        )
        toast.show()
    }
/*
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }*/
}