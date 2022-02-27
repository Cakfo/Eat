package com.samir.eat.main

import android.os.Handler
import android.os.Looper
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.samir.eat.R
import com.samir.eat.base.BaseFragment
import com.samir.eat.databinding.FragmentMainBinding
import com.samir.eat.filter.FilterFragment
import com.samir.eat.main.adapter.RestaurantsAdapter
import com.samir.eat.util.RestaurantTextWatcher
import com.samir.eat.util.gone
import com.samir.eat.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<MainViewModel, FragmentMainBinding>() {

    private val editTextDelay = 200L

    companion object {
        const val REQUEST_KEY = "REQUEST_KEY"
    }

    override val viewModelType: Class<MainViewModel> = MainViewModel::class.java

    override fun getViewDataBinding() = FragmentMainBinding.inflate(layoutInflater)

    override fun viewCreated() {
        setupRecyclerViewScrollListener(binding.recyclerRestaurants) {
            viewModel.loadPaginatedRestaurants()
        }

        setupUi()
        setupObservers()

        setFragmentResultListener(REQUEST_KEY) { _, bundle ->
            val priceLevel = bundle.getInt(FilterFragment.PRICE_LEVEL)
            val cuisinesId = bundle.getString(FilterFragment.CUISINE_ID)
            val neighborhoodsId = bundle.getString(FilterFragment.NEIGHBORHOOD_ID)

            viewModel.run {
                setPriceLevelFilter(
                    if (priceLevel == 0) {
                        null
                    } else {
                        priceLevel
                    }
                )

                setCuisineFilters(cuisinesId)
                setNeighborhoodFilters(neighborhoodsId)
                loadFilteredRestaurants()
            }
        }
    }

    private fun setupObservers() {
        viewModel.restaurants.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.run {
                    recyclerRestaurants.gone()
                    layoutNoRestaurants.visible()
                }
            } else {
                binding.run {
                    recyclerRestaurants.adapter = RestaurantsAdapter(it)
                    recyclerRestaurants.visible()
                    layoutNoRestaurants.gone()
                }
            }
        }
    }

    private fun setupUi() = with(binding) {
        // Used to prevent edit text emitting empty string when screen loads
        Handler(Looper.getMainLooper()).postDelayed({
            binding.editSearch.addTextChangedListener(RestaurantTextWatcher {
                this@MainFragment.viewModel.searchDebounced(it)
            })
        }, editTextDelay)

        binding.buttonFilter.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_filterFragment)
        }
    }
}