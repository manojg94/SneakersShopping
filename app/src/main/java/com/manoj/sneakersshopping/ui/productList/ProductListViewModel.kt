package com.manoj.sneakersshopping.ui.productList

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manoj.sneakersshopping.ui.models.SneakersModel
import com.manoj.sneakersshopping.ui.utils.Utils
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class ProductListViewModel() : ViewModel() {
    private val _product = MutableLiveData<List<SneakersModel>>()
    val product: LiveData<List<SneakersModel>> = _product

    fun getJson(context: Context) {
        val jsonString = Utils.readJson(context, "data/shopping/sneakers.json")
        val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val listType = Types.newParameterizedType(List::class.java, SneakersModel::class.java)
        val adapter: JsonAdapter<List<SneakersModel>> = moshi.adapter(listType)
        val products = adapter.fromJson(jsonString)
        _product.value = products!!
    }
}