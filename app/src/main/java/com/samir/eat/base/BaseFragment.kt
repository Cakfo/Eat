package com.samir.eat.base

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samir.eat.BR
import com.samir.eat.R


abstract class BaseFragment<VM : BaseViewModel, VB : ViewDataBinding> : Fragment() {

    protected lateinit var viewModel: VM
    protected lateinit var binding: VB
    abstract val viewModelType: Class<VM>

    private var loadingDialog: AlertDialog? = null

    protected abstract fun viewCreated()

    protected abstract fun getViewDataBinding(): VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(viewModelType)
        binding = getViewDataBinding().also {
            val viewModelRId = BR.viewModel
            if (viewModelRId != 0) {
                it.run {
                    setVariable(viewModelRId, viewModel)
                    lifecycleOwner = viewLifecycleOwner
                    executePendingBindings()
                }
            }
        }

        setupObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewCreated()
    }

    protected fun setupRecyclerViewScrollListener(recyclerView: RecyclerView, watcher: () -> Unit) {
        var pastVisibleItems: Int
        var visibleItemCount: Int
        var totalItemCount: Int

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) { //check for scroll down
                    val layoutManager = recyclerView.layoutManager!! as LinearLayoutManager
                    visibleItemCount = layoutManager.childCount
                    totalItemCount = layoutManager.itemCount
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                    if (!viewModel.isLoading.value!!) {
                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                            watcher.invoke()
                        }
                    }
                }
            }
        })
    }

    private fun setupObservers() = with(viewModel) {
        error.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        isLoading.observe(viewLifecycleOwner, { loading ->
            loading?.let {
                if (it) {
                    showProgressDialog()
                } else {
                    hideProgressDialog()
                }
            }
        })
    }

    private fun showProgressDialog() {
        if (loadingDialog == null) {
            val builder = AlertDialog.Builder(requireContext())
            builder.setCancelable(false)

            builder.setView(R.layout.loading_layout)
            loadingDialog = builder.create()
            loadingDialog!!.show()
        } else {
            loadingDialog!!.show()
        }
    }

    private fun hideProgressDialog() {
        loadingDialog?.let { loadingDialog ->
            if (loadingDialog.isShowing) {
                loadingDialog.hide()
            }
        }
    }
}