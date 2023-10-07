package com.manoj.sneakersshopping.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.manoj.sneakersshopping.databinding.CartItemCardLayoutBinding
import com.manoj.sneakersshopping.ui.models.SneakersModel

class CartAdapter() : ListAdapter<SneakersModel, RecyclerView.ViewHolder>(SneakersModel.diffCallback) {

        private lateinit var binding: CartItemCardLayoutBinding
        var onItemClick: ((SneakersModel) -> Unit)? = null
        var onPlusItemClick: ((SneakersModel) -> Unit)? = null
        var onMinusItemClick: ((SneakersModel) -> Unit)? = null
        var onDeleteItemClick: ((SneakersModel) -> Unit)? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            binding = CartItemCardLayoutBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false)
            return ItemViewHolder(binding)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (holder is ItemViewHolder) {
                holder.bind(getItem(position))

                holder.itemView.setOnClickListener() {
                    onItemClick?.invoke(getItem(position))
                }
                binding.increaseButton.setOnClickListener() {
                    onPlusItemClick?.invoke(getItem(position))
                }
                binding.decreaseButton.setOnClickListener() {
                    onMinusItemClick?.invoke(getItem(position))
                }
                binding.productRemove.setOnClickListener() {
                    onDeleteItemClick?.invoke(getItem(position))
                }
            }
        }

        class ItemViewHolder(private val binding: CartItemCardLayoutBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(sneakersModel: SneakersModel) {
                binding.bModel = sneakersModel
                binding.productImage.load(sneakersModel.image)
            }
        }

    }