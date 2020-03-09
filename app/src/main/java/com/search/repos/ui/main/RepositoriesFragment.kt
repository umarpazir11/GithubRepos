package com.search.repos.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.search.repos.databinding.RepositoriesFragmentBinding
import com.search.repos.di.Injectable
import com.search.repos.di.factory.injectViewModel
import com.search.repos.ui.hide
import com.search.repos.ui.show
import com.test.mynewsapp.api.Result
import javax.inject.Inject


class RepositoriesFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: RepositoriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = injectViewModel(viewModelFactory)
        setHasOptionsMenu(true)

        val binding = RepositoriesFragmentBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val adapter = RepositoriesAdapter()
        binding.recyclerView.adapter = adapter

        binding.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotEmpty()) {
                    binding.progressBar.show()
                    viewModel.searchRepos(newText)
                }
                return true
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    binding.progressBar.show()
                    viewModel.searchRepos(query)
                }
                return true
            }
        })
        subscribeUi(binding, adapter)

        return binding.root

    }

    /**
     * Observe live data that comes from API and on SUCCESS binding it to Recyclerview Adapter
     *
     * On LOADING showing progress bar
     *
     * On error hiding progress bar and showing error msg on Snackbar
     */
    private fun subscribeUi(binding: RepositoriesFragmentBinding, adapter: RepositoriesAdapter) {
        viewModel.liveDataResults.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Result.Status.SUCCESS -> {
                    binding.progressBar.hide()
                    adapter.submitList(it.data?.items)
                }
                Result.Status.LOADING -> binding.progressBar.show()
                Result.Status.ERROR -> {
                    binding.progressBar.hide()
                    Snackbar.make(binding.constraintLayout, it.message!!, Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        })
    }
}


