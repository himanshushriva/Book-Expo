package com.himan.bookexpo.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.himan.bookexpo.R
import com.himan.bookexpo.databinding.FragmentBookDetailsBinding

class BookDetailsFragment : Fragment(R.layout.fragment_book_details) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentBookDetailsBinding.bind(view)


    }
}