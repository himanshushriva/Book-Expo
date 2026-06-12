package com.himan.bookexpo.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.himan.bookexpo.data.repository.BookRepository
import kotlinx.coroutines.launch

class BookDetailsViewModel(
    private val repository: BookRepository
) : ViewModel() {

    fun loadBookDetails(id: String) {
        viewModelScope.launch {
            val bookDetails = repository.getBookDetails(id)

        }
    }
}