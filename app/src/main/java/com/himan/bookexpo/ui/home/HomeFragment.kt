package com.himan.bookexpo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.himan.bookexpo.data.remote.ApiClient
import com.himan.bookexpo.data.repository.BookRepository
import com.himan.bookexpo.databinding.FragmentHomeBinding

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
        Toast.makeText(requireContext(), "Book clicked: ${book.title}",
            Toast.LENGTH_SHORT).show()
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
        observeUi()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        binding.rvBooks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvBooks.adapter = adapter
    }

    private fun observeUi() {
        viewModel.books.observe(viewLifecycleOwner) { books ->
            adapter.submitList(books)
        }
    }
}