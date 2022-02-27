package com.samir.eat.ui.splash

import androidx.navigation.fragment.findNavController
import com.samir.eat.R
import com.samir.eat.base.BaseFragment
import com.samir.eat.databinding.FragmentResourcesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResourcesFragment : BaseFragment<ResourcesViewModel, FragmentResourcesBinding>() {
    
    override val viewModelType = ResourcesViewModel::class.java
    override fun getViewDataBinding() = FragmentResourcesBinding.inflate(layoutInflater)

    override fun viewCreated() {
        viewModel.finishedLoading.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_resourcesFragment_to_mainFragment)
        }
    }
}