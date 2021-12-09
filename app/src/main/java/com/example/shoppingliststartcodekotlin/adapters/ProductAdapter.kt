package com.example.shoppingliststartcodekotlin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingliststartcodekotlin.R
import com.example.shoppingliststartcodekotlin.data.Repository
import com.example.shoppingliststartcodekotlin.models.Product
import com.google.android.material.snackbar.Snackbar


class ProductAdapter(var products: MutableList<Product>) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.productitem, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        holder.itemName.text = products[position].name
        holder.itemQuantity.text = products[position].quantity.toString()
    }

    override fun getItemCount(): Int {
        return products.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //here you need to to do stuff also - go back to the exercises
        //about recyclerviews and you can use approach that were used
        //in the exercise about recyclerviews from the book (lesson 3)
        //if you did not do that exercise - then first do that exercise in
        //a seperate project to learn about using a ViewHolder

        var itemName: TextView
        var itemQuantity: TextView
        var itemImage: ImageView
        var itemDelete: ImageButton


        init {
            itemName = itemView.findViewById(R.id.item_name)
            itemQuantity = itemView.findViewById(R.id.item_quantity)
            itemImage = itemView.findViewById(R.id.item_image)
            itemDelete = itemView.findViewById(R.id.item_delete_btn)

            itemView.setOnClickListener { v: View ->
                var position: Int = getAdapterPosition()
                Snackbar.make(v, "Click detected on item $position", Snackbar.LENGTH_LONG).setAction("Action", null).show()
                }

            itemDelete.setOnClickListener { v: View ->
                val position = adapterPosition
                Repository.deleteProduct(position)
                notifyItemRemoved(position) //this line notify the adapter
            }
        }
    }
}
