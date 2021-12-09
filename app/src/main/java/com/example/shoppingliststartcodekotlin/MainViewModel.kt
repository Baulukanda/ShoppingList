package com.example.shoppingliststartcodekotlin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppingliststartcodekotlin.models.Product
import com.example.shoppingliststartcodekotlin.data.Repository

class MainViewModel: ViewModel() {

    fun getData(): MutableLiveData<MutableList<Product>> {
        return Repository.getData()
    }

}