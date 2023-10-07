package com.manoj.sneakersshopping.ui.productList

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.manoj.sneakersshopping.databinding.FragmentProductListBinding
import com.manoj.sneakersshopping.ui.models.SneakersModel

class ProductListFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private val TAG = "ProductListFragment"
    private lateinit var binding: FragmentProductListBinding
    private val viewModel: ProductListViewModel by viewModels()
    private var mAdapter: ProductListAdapter = ProductListAdapter()
    private val args:ProductListFragmentArgs by navArgs()
    var sortBy = arrayOf<String?>("SortBy", "Popular", "Rating","Orders")
    lateinit var dataFilter: List<SneakersModel>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentProductListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.productTitle.text = "Sneakers"

        binding.productRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.productRecyclerView.adapter = mAdapter

        spinnerImp()
        observeFromViewModal()
        viewModel.getJson(requireContext())


    }

    private fun spinnerImp() {

        binding.filterSpinner.onItemSelectedListener = this

        val ad: ArrayAdapter<*> = ArrayAdapter<Any?>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            sortBy)

        ad.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item)

        binding.filterSpinner.adapter = ad

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if(this::dataFilter.isInitialized){
            when(sortBy[position]){
                "SortBy" -> mAdapter.submitList(dataFilter)
                "Popular" ->mAdapter.submitList(dataFilter.sortedBy {it.popular})
                "Rating" ->mAdapter.submitList(dataFilter.sortedBy {it.rating})
                "Orders" ->mAdapter.submitList(dataFilter.sortedBy {it.orders})
            }
            mAdapter.notifyDataSetChanged()
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun setupSearch(sneakersModels: List<SneakersModel>) {
        with(binding) {
            searchBar.doOnTextChanged { text, _, _, _ ->
                text?.let {
                  //  showProgress()
                    if (it.isNotEmpty() && it.length >= 2) {
                        val searchedList = arrayListOf<SneakersModel>()
                        sneakersModels.forEach { taskDetailsItem ->
                            val searchText =
                                "${taskDetailsItem.title}*${taskDetailsItem.subtitle}*" +
                                        "${taskDetailsItem.material}*${taskDetailsItem.price}"
                            if (searchText.contains(text, true)) {
                                searchedList.add(taskDetailsItem)
                            }
                        }
                        mAdapter.submitList(searchedList)
                        mAdapter.notifyDataSetChanged()
                    } else {
                        mAdapter.submitList(sneakersModels)
                        mAdapter.notifyDataSetChanged()
                    }
                  //  hideProgress()
                }
            }
        }
    }


    private fun observeFromViewModal() {
        viewModel.product.observe(requireActivity()) {
            mAdapter.submitList(it)
            dataFilter = it
            setupSearch(it)
            mAdapter.onItemClick = {
                val bottomSheetFragment: BottomSheetDialogFragment = ProductDetailsBottomSheetFragment(it)
                bottomSheetFragment.show(requireFragmentManager(), bottomSheetFragment.tag)
                Log.i(TAG, "on item Clicked:  ${it.title}")
            }
        }
    }
}