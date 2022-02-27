package com.samir.eat.ui.cuisines

import android.os.Bundle
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.samir.eat.base.BaseFragment
import com.samir.eat.databinding.FragmentCommonBinding
import com.samir.eat.ui.common.SelectionAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CuisinesFragment : BaseFragment<CuisinesViewModel, FragmentCommonBinding>() {

    companion object {
        const val REQUEST_KEY = "CUISINES_REQUEST_KEY"
        const val CUISINES_ID = "CUISINE_ID"
        const val CUISINES_NAME = "CUISINE_NAME"
    }

    private val adapter = SelectionAdapter()

    override fun getViewDataBinding() = FragmentCommonBinding.inflate(layoutInflater)
    override val viewModelType = CuisinesViewModel::class.java

    override fun viewCreated() {
        setupUi()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.cuisines.observe(viewLifecycleOwner) {
            adapter.updateData(it)
        }
    }

    private fun setupUi() = with(binding) {
        recyclerCommon.adapter = adapter
        buttonApplyFilters.setOnClickListener {
            val result = Bundle().apply {
                putString(CUISINES_ID, adapter.selectedItem?.id)
                putString(CUISINES_NAME, adapter.selectedItem?.attributes?.name)
            }
            setFragmentResult(REQUEST_KEY, result)
            findNavController().navigateUp()
        }

        imageBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}