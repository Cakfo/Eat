package com.samir.eat.ui.filter

import android.os.Bundle
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.samir.eat.R
import com.samir.eat.base.BaseFragment
import com.samir.eat.ui.cuisines.CuisinesFragment
import com.samir.eat.databinding.FragmentFilterBinding
import com.samir.eat.networking.data.ResourceManager
import com.samir.eat.ui.main.RestaurantsFragment
import com.samir.eat.ui.neighborhoods.NeighborhoodsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterFragment : BaseFragment<FilterViewModel, FragmentFilterBinding>() {

    companion object {
        const val PRICE_LEVEL = "KEY_PRICE_LEVEL"
        const val CUISINE_ID = "KEY_CUISINE"
        const val NEIGHBORHOOD_ID = "KEY_NEIGHBORHOOD"
    }

    override val viewModelType = FilterViewModel::class.java

    override fun getViewDataBinding() = FragmentFilterBinding.inflate(layoutInflater)

    override fun viewCreated() {

        setupUi()
        setupObservers()

        setFragmentResultListener(NeighborhoodsFragment.REQUEST_KEY) { _, bundle ->
            viewModel.setNeighborhoodId(bundle.getString(NeighborhoodsFragment.NEIGHBORHOODS_ID))
            viewModel.setNeighborhoodName(bundle.getString(NeighborhoodsFragment.NEIGHBORHOODS_NAME))
        }

        setFragmentResultListener(CuisinesFragment.REQUEST_KEY) { _, bundle ->
            viewModel.setCuisineId(bundle.getString(CuisinesFragment.CUISINES_ID))
            viewModel.setCuisineName(bundle.getString(CuisinesFragment.CUISINES_NAME))
        }
    }

    private fun setupObservers() {
        viewModel.selectedCuisineName.observe(this) {
            binding.textCuisineFilters.text =
                String.format("(%s)", it ?: getString(R.string.all_label).lowercase())
        }

        viewModel.selectedNeighborhoodName.observe(this) {
            binding.textNeighborhoodFilters.text =
                String.format("(%s)", it ?: getString(R.string.all_label).lowercase())
        }
    }

    private fun setupUi() = with(binding) {
        textApply.setOnClickListener {
            applyFilters()
        }

        buttonApplyFilters.setOnClickListener {
            applyFilters()
        }

        layoutCuisine.setOnClickListener {
            findNavController().navigate(R.id.action_filterFragment_to_cuisinesFragment)
        }

        layoutNeighborhood.setOnClickListener {
            findNavController().navigate(R.id.action_filterFragment_to_neighborhoodsFragment)
        }

        textCancel.setOnClickListener {
            findNavController().navigateUp()
        }

        ResourceManager.neighborhoods.find { it.selected }?.let {
            textNeighborhoodFilters.text = String.format("(%s)", it.attributes?.name)
        }
        ResourceManager.cuisines.find { it.selected }?.let {
            textCuisineFilters.text = String.format("(%s)", it.attributes?.name)
        }
    }

    private fun applyFilters() {
        val result = Bundle().apply {
            putInt(PRICE_LEVEL, this@FilterFragment.viewModel.priceLevel.value?.value ?: 0)
            putString(
                CUISINE_ID,
                this@FilterFragment.viewModel.selectedCuisineId.value
            )
            putString(
                NEIGHBORHOOD_ID,
                this@FilterFragment.viewModel.selectedNeighborhoodId.value
            )
        }
        setFragmentResult(RestaurantsFragment.REQUEST_KEY, result)
        findNavController().navigateUp()
    }
}