package com.samir.eat.ui.neighborhoods

import android.os.Bundle
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.samir.eat.R
import com.samir.eat.base.BaseFragment
import com.samir.eat.ui.common.SelectionAdapter
import com.samir.eat.databinding.FragmentCommonBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NeighborhoodsFragment : BaseFragment<NeighborhoodsViewModel, FragmentCommonBinding>() {

    companion object {
        const val REQUEST_KEY = "NEIGHBORHOOD_REQUEST_KEY"
        const val NEIGHBORHOODS_ID = "NEIGHBORHOODS_ID"
        const val NEIGHBORHOODS_NAME = "NEIGHBORHOODS_NAME"
    }

    private val adapter = SelectionAdapter()

    override fun getViewDataBinding() = FragmentCommonBinding.inflate(layoutInflater)
    override val viewModelType = NeighborhoodsViewModel::class.java

    override fun viewCreated() {
        setupRecyclerViewScrollListener(binding.recyclerCommon) {
            viewModel.loadPaginatedNeighborhoods()
        }

        setupUi()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.neighborhoods.observe(this) {
            adapter.updateData(it)
        }
    }

    private fun setupUi() = with(binding) {
        textTitle.text = requireContext().getString(R.string.neighborhoods_label)
        textSubtitle.text = requireContext().getString(R.string.neighborhoods_label)
        recyclerCommon.adapter = adapter

        buttonApplyFilters.setOnClickListener {
            val result = Bundle().apply {
                putString(NEIGHBORHOODS_ID, adapter.selectedItem?.id)
                putString(NEIGHBORHOODS_NAME, adapter.selectedItem?.attributes?.name)
            }
            setFragmentResult(REQUEST_KEY, result)
            findNavController().navigateUp()
        }

        imageBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}