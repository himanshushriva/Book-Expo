package com.himan.bookexpo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.himan.bookexpo.R
import com.himan.bookexpo.data.remote.ApiClient
import com.himan.bookexpo.data.repository.BookRepository
import com.himan.bookexpo.databinding.FragmentHomeBinding
import com.himan.bookexpo.ui.details.BookDetailsFragment

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding!!

    private val repository: BookRepository by lazy {
        BookRepository(ApiClient.bookApi)
    }

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(repository)
    }

    private val adapter = BookAdapter { book ->
        viewModel.onBookClicked(book)
    }

    companion object {
        private const val TAG = "HomeFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        observeUiState()
        observeEvents()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        binding.rvBooks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvBooks.adapter = adapter
    }

    private fun observeUiState() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->

            binding.progressIndicator.isVisible = state.isLoading

            binding.tvError.isVisible = state.errorMessage != null
            binding.tvError.text = state.errorMessage?.ifEmpty { "Some error occurred!" }

            adapter.submitList(state.books)
        }
    }

    private fun observeEvents() {
        viewModel.events.observe(viewLifecycleOwner) { event ->

            when (event) {
                is HomeEvent.OpenBookDetails -> {
                    navigateToDetails(event.bookId)

                    viewModel.clearEvent()
                }

                null -> Unit
            }
        }
    }

    private fun navigateToDetails(bookId: String) {

        parentFragmentManager.commit {
            replace<BookDetailsFragment>(
                R.id.fragmentContainer,
                args = bundleOf(
                    BookDetailsFragment.ARG_BOOK_ID to bookId
                )
            )
            addToBackStack(null)
        }
    }
}