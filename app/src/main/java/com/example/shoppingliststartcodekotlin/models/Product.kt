package com.example.shoppingliststartcodekotlin.models

import android.os.Parcelable
import com.google.firebase.firestore.Exclude
import kotlinx.parcelize.Parcelize


//The exclude thing on product is because we want to have an id number in the product
//class, but do not want to save it to firebase as firebase will automatically
//generate the IDs (in this application, maybe in other applications you do it
//differently.
@Parcelize

data class Product(var name:String = "",  var quantity: Int = 0, var img: String? = null,
                   @get:Exclude var id: String = "") : Parcelable
