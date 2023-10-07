package com.manoj.sneakersshopping.ui.productList

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import coil.load
import com.manoj.sneakersshopping.ui.models.SneakersModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.manoj.sneakersshopping.databinding.ProductSItemBottomSheetLayoutBinding
import com.manoj.sneakersshopping.ui.MainActivity.Companion.cartItems

class ProductDetailsBottomSheetFragment(private val cloth: SneakersModel) :
    BottomSheetDialogFragment() {
    private lateinit var binding: ProductSItemBottomSheetLayoutBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProductSItemBottomSheetLayoutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.image.load(cloth.image)
        binding.title.text = cloth.title
        binding.subtitle.text = cloth.subtitle
        binding.material.text = cloth.material
        binding.quality.text = cloth.quality
        binding.size.text = cloth.size
        binding.price.text = cloth.price

        binding.addToCart.setOnClickListener {
            try {
                for (i in cartItems) {
                    if (i.key == cloth.key) {
                        cloth.quantity = cloth.quantity + 1
                        cartItems.remove(cloth)
                    }
                }
                cartItems.add(cloth)
                if (cloth.quantity > 1) {
                    Toast.makeText(
                        context,
                        cloth.quantity.toString() + " Items Added to Cart.",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    Toast.makeText(context, "Item Added to Cart.", Toast.LENGTH_SHORT)
                        .show()
                }
            } catch (e: Exception) {
                print(e.message)
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener { setupBottomSheet(it) }
        return dialog
    }

    private fun setupBottomSheet(dialogInterface: DialogInterface) {
        val bottomSheetDialog = dialogInterface as BottomSheetDialog
        val bottomSheet = bottomSheetDialog.findViewById<View>(
            com.google.android.material.R.id.design_bottom_sheet
        )
            ?: return
        bottomSheet.setBackgroundColor(Color.TRANSPARENT)

        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
}