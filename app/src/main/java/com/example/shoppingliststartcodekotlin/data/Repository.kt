package com.example.shoppingliststartcodekotlin.data

import android.util.Log
import android.util.SparseBooleanArray
import android.widget.ArrayAdapter
import androidx.lifecycle.MutableLiveData
import com.example.shoppingliststartcodekotlin.models.Product
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

object Repository {
    var products = mutableListOf<Product>()
    //listener to changes that we can then use in the Activity
    private var productListener = MutableLiveData<MutableList<Product>>()
    private lateinit var adapter: ArrayAdapter<Product>
    private lateinit var db: FirebaseFirestore

    fun getData(): MutableLiveData<MutableList<Product>> {
        db = Firebase.firestore
        db.collection("products").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("Repository", "${document.id} => ${document.data}")
                    val product = document.toObject<Product>()
                    product.id = document.id  //set the ID in the product class
                    products.add(product)
                }
                productListener.value = products //notify our listener we have new data
            }
            .addOnFailureListener { exception ->
                Log.d("Repository", "Error getting documents: ", exception)
            }
        return productListener
    }

    fun createTestData()
    {
        //add some products to the products list - for testing purposes
        Log.d("Repository","create testdata")
        products.add(Product(name="Potato", quantity = 2, img="potato.gif"))
        products.add(Product(name="Beans",quantity = 4, img="beans.gif"))
        products.add(Product(name="Apple", quantity = 10, img="apple.gif"))
        products.add(Product(name="Milk", quantity = 1, img="milk.gif"))
    }

    fun addProduct(product : Product){
        //products.add(product);
        db.collection("products")
            .add(product)
            .addOnSuccessListener { documentReference ->
                Log.d("Error", "DocumentSnapshot written with ID: " + documentReference.id)
            }
            .addOnFailureListener { e -> Log.w("Error", "Error adding document", e) }
    }

    fun deleteProduct(index : Int) {

        //productListener.value = products

        val product = products[index]
        db.collection("products").document(product.id).delete().addOnSuccessListener {
            Log.d("Snapshot","DocumentSnapshot with id: ${product.id} successfully deleted!")
            //products.removeAt(index) //removes it from the list
        }
            .addOnFailureListener { e -> Log.w("Error", "Error deleting document", e) }
        products.removeAt(index)
        productListener.value = products
    }

    fun deleteAllProducts(){
        val batch = db.batch()
        for (product in products) {
            val ref = db.collection("products").document(product.id)
            batch.delete(ref)
        }

        // Commit the batch
        batch.commit().addOnCompleteListener {
            products.clear()
            adapter.notifyDataSetChanged()
        }
    }


    fun updateProduct(product: Product, name: String, quantity: String, img: String) {
        db.collection("products").document(product.id)
            .update("name", name, "quantity", quantity, "img",img)
    }
}