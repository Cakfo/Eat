package com.samir.eat.filter

import android.os.Bundle
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.samir.eat.R
import com.samir.eat.base.BaseFragment
import com.samir.eat.cuisines.CuisinesFragment
import com.samir.eat.databinding.FragmentFilterBinding
import com.samir.eat.main.MainFragment
import com.samir.eat.neighborhoods.NeighborhoodsFragment
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

        viewModel.selectedCuisineName.observe(this) {
            binding.textCuisineFilters.text = String.format("(%s)", it)
        }

        viewModel.selectedNeighborhoodName.observe(this) {
            binding.textNeighborhoodFilters.text = String.format("(%s)", it)
        }

        setFragmentResultListener(NeighborhoodsFragment.REQUEST_KEY) { _, bundle ->
            bundle.getString(NeighborhoodsFragment.NEIGHBORHOODS_ID)?.let {
                viewModel.setNeighborhoodId(it)
            }
            bundle.getString(NeighborhoodsFragment.NEIGHBORHOODS_NAME)?.let {
                viewModel.setNeighborhoodName(it)
            }
        }

        setFragmentResultListener(CuisinesFragment.REQUEST_KEY) { _, bundle ->
            bundle.getString(CuisinesFragment.CUISINES_ID)?.let {
                viewModel.setCuisineId(it)
            }
            bundle.getString(CuisinesFragment.CUISINES_NAME)?.let {
                viewModel.setCuisineName(it)
            }
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
    }

    private fun applyFilters() {
        val result = Bundle().apply {
            putInt(PRICE_LEVEL, this@FilterFragment.viewModel.priceLevel.value!!)
            putString(
                CUISINE_ID,
                this@FilterFragment.viewModel.selectedCuisineId.value
            )
            putString(
                NEIGHBORHOOD_ID,
                this@FilterFragment.viewModel.selectedNeighborhoodId.value
            )
        }
        setFragmentResult(MainFragment.REQUEST_KEY, result)
        findNavController().navigateUp()
    }
}