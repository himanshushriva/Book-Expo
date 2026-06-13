package com.himan.bookexpo.ui.details

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
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

        observeUiState(binding)

        viewModel.loadBookDetails(bookId)
    }

    private fun observeUiState(binding: FragmentBookDetailsBinding) {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->

            // Loading
            binding.progressIndicator.isVisible = state.isLoading

            // Error
            binding.tvError.isVisible =
                state.errorMessage != null
            binding.tvError.text =
                state.errorMessage?.ifEmpty { "Some error occurred!" }

            val bookDetails = state.bookDetails ?: return@observe

            // Success
            binding.apply {
                labelGroup.isVisible = true

                tvTitle.text = bookDetails.title
                tvSubtitle.text = bookDetails.subtitle
                tvAuthors.text = bookDetails.authors.ifEmpty { "NA" }
                tvPublisher.text = bookDetails.publisher.ifEmpty { "NA" }
                tvYear.text = bookDetails.year.ifEmpty { "NA" }
                tvPages.text = bookDetails.pages.ifEmpty { "NA" }
                tvDescription.text = bookDetails.description.ifEmpty { "NA" }

                sivBookImage.setImageResource(R.drawable.book_placeholder)
            }
        }
    }
}