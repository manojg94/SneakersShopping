package com.manoj.sneakersshopping.ui.productList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.manoj.sneakersshopping.databinding.ProductSItemCardLayoutBinding
import com.manoj.sneakersshopping.ui.models.SneakersModel

class ProductListAdapter :
    ListAdapter<SneakersModel, RecyclerView.ViewHolder>(SneakersModel.diffCallback) {

    private lateinit var binding: ProductSItemCardLayoutBinding
    var onItemClick: ((SneakersModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ProductSItemCardLayoutBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.bind(getItem(position))

            holder.itemView.setOnClickListener() {
                try {
                    onItemClick?.invoke(getItem(position))
                }catch (e:Exception){
                    print(e.message)
                }
            }
        }
    }

    class ItemViewHolder(private val binding: ProductSItemCardLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(sneakersModel: SneakersModel) {
            binding.bModel = sneakersModel
            binding.image.load(sneakersModel.image)
        }
    }

    //    var onItemClick : ((SneakersModel)-> Unit)? = null
//
//    fun loadData(newProducts: List<SneakersModel>) {
//        products = newProducts
//        notifyDataSetChanged()
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.product_s_item_card_layout, parent, false)
//        return ProductViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
//        val product = products[position]
//
//        holder.title.text = product.title
//        holder.subtitle.text = product.subtitle
//        holder.price.text = product.price
//        holder.image.load(product.image)
//
//        holder.itemView.setOnClickListener(){
//            onItemClick?.invoke(product)
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return products.size
//    }
//
//    class ProductViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
//        val title: TextView = itemView.findViewById(R.id.title)
//        val subtitle: TextView = itemView.findViewById(R.id.subtitle)
//        val price: TextView = itemView.findViewById(R.id.price)
//        val image: ImageView = itemView.findViewById(R.id.image)
//    }

}