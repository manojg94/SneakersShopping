package com.manoj.sneakersshopping.ui.models

import androidx.recyclerview.widget.DiffUtil

data class SneakersModel(
    val key:String,
    val image: String,
    val material: String,
    val price: String,
    val quality: String,
    val popular: String,
    val rating: String,
    val orders: String,
    var quantity: Int = 1,
    val size: String,
    val subtitle: String,
    val title: String
){
    companion object {
        var diffCallback: DiffUtil.ItemCallback<SneakersModel> =
            object : DiffUtil.ItemCallback<SneakersModel>() {

                override fun areItemsTheSame(
                    oldItem: SneakersModel,
                    newItem: SneakersModel
                ): Boolean {
                    return oldItem.key == newItem.key
                }

                override fun areContentsTheSame(
                    oldItem: SneakersModel,
                    newItem: SneakersModel
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}