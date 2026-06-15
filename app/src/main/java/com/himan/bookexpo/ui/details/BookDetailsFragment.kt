package com.himan.bookexpo.ui.details

import android.content.pm.PackageManager
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.himan.bookexpo.R
import com.himan.bookexpo.data.model.BookDetails
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
        observeEvents()

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

                val decodedBookDetails = decodeHtml(bookDetails)

                tvTitle.text = decodedBookDetails.title
                tvSubtitle.text = decodedBookDetails.subtitle
                tvAuthors.text = decodedBookDetails.authors.ifEmpty { "NA" }
                tvPublisher.text = decodedBookDetails.publisher.ifEmpty { "NA" }
                tvYear.text = decodedBookDetails.year.ifEmpty { "NA" }
                tvPages.text = decodedBookDetails.pages.ifEmpty { "NA" }

                setDescription(
                    decodedBookDetails.description.ifEmpty { "NA" },
                    decodedBookDetails.url,
                    binding
                )

                sivBookImage.setImageResource(R.drawable.book_placeholder)
            }
        }
    }

    private fun observeEvents() {
        viewModel.events.observe(viewLifecycleOwner) { event ->
            when (event) {
                is BookDetailsEvent.OpenBookUrl -> {
                    openBookUrl(event.url)
                    viewModel.clearEvent()
                }

                else -> Unit
            }
        }
    }

    private fun decodeHtml(bookDetails: BookDetails): BookDetails {

        return bookDetails.copy(
            title = decodeHtml(bookDetails.title),
            subtitle = decodeHtml(bookDetails.subtitle),
            authors = decodeHtml(bookDetails.authors),
            publisher = decodeHtml(bookDetails.publisher),
            description = decodeHtml(bookDetails.description),
        )
    }

    private fun decodeHtml(encodedText: String): String {
        return HtmlCompat.fromHtml(
            encodedText,
            HtmlCompat.FROM_HTML_MODE_LEGACY
        ).toString()
    }

    private fun setDescription(
        description: String,
        url: String,
        binding: FragmentBookDetailsBinding
    ) {

        val readMoreText = "Read More"

        val fullText = "$description $readMoreText"

        val spannable = SpannableString(fullText)

        val start = fullText.indexOf(readMoreText)

        val end = start + readMoreText.length

        spannable.setSpan(
            object : ClickableSpan() {

                override fun onClick(widget: View) {
                    viewModel.onReadMoreClicked(url)
                }
            },
            start,
            end,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.tvDescription.text = spannable
        binding.tvDescription.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun openBookUrl(url: String) {
        val customTabsIntent = CustomTabsIntent.Builder().build()

        val isChromeInstalled = try {
            requireContext().packageManager
                .getPackageInfo("com.android.chrome", 0)
            true
        } catch (_: PackageManager.NameNotFoundException) {
            false
        }

        if (isChromeInstalled) {
            // Explicitly set Chrome as the handler
            customTabsIntent.intent.`package` = "com.android.chrome"
        }

        customTabsIntent.launchUrl(requireContext(), url.toUri())
    }
}