package com.manoj.sneakersshopping.ui.cart

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.manoj.sneakersshopping.databinding.FragmentCartBinding
import com.manoj.sneakersshopping.ui.MainActivity.Companion.cartItems
import com.manoj.sneakersshopping.ui.models.SneakersModel
import com.manoj.sneakersshopping.ui.productList.ProductDetailsBottomSheetFragment


class CartFragment() : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private var mAdapter: CartAdapter = CartAdapter()
    val taxes: Int = 0 // static tax

    private val TAG = "CartFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener(View.OnClickListener {
            val action = CartFragmentDirections.actionBackToHome()
            findNavController().navigate(action)
        })
        binding.recyclerviewCart.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerviewCart.adapter = mAdapter
        mAdapter.submitList(cartItems)
        mAdapter.onItemClick = {
            val bottomSheetFragment: BottomSheetDialogFragment =
                ProductDetailsBottomSheetFragment(it)
            bottomSheetFragment.show(requireFragmentManager(), bottomSheetFragment.tag)
            Log.i(TAG, "on item Clicked:  ${it.title}")
        }
        mAdapter.onPlusItemClick = {
            if (it.quantity > 0) {
                it.quantity = it.quantity + 1
            } else {
                cartItems.remove(it)
            }

            if (it.quantity == 0) {
                cartItems.remove(it)
            }
            mAdapter.notifyDataSetChanged()
            calculateTotal(cartItems)

        }
        mAdapter.onMinusItemClick = {
            if (it.quantity > 0) {
                it.quantity = it.quantity - 1
            } else {
                cartItems.remove(it)
            }
            if (it.quantity == 0) {
                cartItems.remove(it)
            }
            mAdapter.notifyDataSetChanged()
            calculateTotal(cartItems)
        }
        mAdapter.onDeleteItemClick = {
            cartItems.remove(it)
            mAdapter.notifyDataSetChanged()
            calculateTotal(cartItems)
        }
        calculateTotal(cartItems)
    }

    private fun calculateTotal(cartItems: MutableList<SneakersModel>) {
        var subTotal = 0
        for (i in cartItems) {
            subTotal += (i.price.split(" ")[1].toInt() * i.quantity)
        }
        val total = subTotal + taxes
        binding.amountSubtotal.text = "\u20B9 $subTotal"
        binding.amountTax.text = "\u20B9 $taxes"
        binding.amountTotal.text = "\u20B9 $total"
    }
}