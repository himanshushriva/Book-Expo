package com.himan.bookexpo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.himan.bookexpo.data.model.Book
import com.himan.bookexpo.data.repository.BookRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: BookRepository) : ViewModel() {

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> = _books

    init {
        loadBooks()     // calling here, not in the HomeFragment to avoid network call again after the Fragment recreation
    }

    fun loadBooks() {
        viewModelScope.launch {
            _books.value = repository.getBooks()
        }
    }
}