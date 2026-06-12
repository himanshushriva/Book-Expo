package com.himan.bookexpo.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.himan.bookexpo.R
import com.himan.bookexpo.data.remote.ApiClient
import com.himan.bookexpo.data.repository.BookRepository
import com.himan.bookexpo.databinding.FragmentBookDetailsBinding

class BookDetailsFragment : Fragment(R.layout.fragment_book_details) {

    private val repository: BookRepository by lazy {
        BookRepository(ApiClient.bookApi)
    }

    private val viewModel: BookDetailsViewModel by viewModels {
        BookDetailsViewModelFactory(repository)
    }

    companion object {
        const val ARG_BOOK_ID = "arg_book_id"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentBookDetailsBinding.bind(view)

        val bookId = requireArguments().getString(ARG_BOOK_ID)!!
        binding.tvBookDetails.text = bookId

        observeUiState(binding)

        viewModel.loadBookDetails(bookId)
    }

    private fun observeUiState(binding: FragmentBookDetailsBinding) {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->

            binding.tvBookDetails.text = buildString {
                appendLine("bookDetails = ${state.bookDetails}")
                appendLine("isLoading = ${state.isLoading}")
                append("errorMessage = ${state.errorMessage}")
            }
        }
    }
}