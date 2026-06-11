package com.himan.bookexpo.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.himan.bookexpo.R
import com.himan.bookexpo.databinding.FragmentBookDetailsBinding

class BookDetailsFragment : Fragment(R.layout.fragment_book_details) {

    companion object {
        const val ARG_BOOK_ID = "arg_book_id"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentBookDetailsBinding.bind(view)

        val bookId = requireArguments().getString(ARG_BOOK_ID)!!
        binding.tvBookDetails.text = bookId
    }
}