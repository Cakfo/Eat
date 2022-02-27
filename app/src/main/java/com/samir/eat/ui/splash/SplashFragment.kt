package com.samir.eat.ui.splash

import androidx.navigation.fragment.findNavController
import com.samir.eat.base.BaseFragment
import com.samir.eat.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashViewModel, FragmentSplashBinding>() {

    override val viewModelType = SplashViewModel::class.java
    override fun getViewDataBinding() = FragmentSplashBinding.inflate(layoutInflater)

    override fun viewCreated() {
        viewModel.restaurantResponse.observe(viewLifecycleOwner) {
            val action =
                SplashFragmentDirections.actionSplashFragmentToMainFragment(it)
            findNavController().navigate(action)
        }
    }
}