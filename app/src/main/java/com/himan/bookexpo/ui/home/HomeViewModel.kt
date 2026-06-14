package com.himan.bookexpo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.himan.bookexpo.data.model.Book
import com.himan.bookexpo.data.repository.BookRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: BookRepository) : ViewModel() {

    private val _uiState = MutableLiveData(HomeUiState())
    val uiState: LiveData<HomeUiState> = _uiState

    private val _events = MutableLiveData<HomeEvent?>()
    val events: LiveData<HomeEvent?> = _events

    init {
        loadBooks()     // calling here, not in the HomeFragment to avoid network call again after the Fragment recreation
    }

    fun loadBooks() {
        viewModelScope.launch {
            _uiState.value = _uiState.value?.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val fetchedBooks = repository.getBooks()

                _uiState.value = HomeUiState(
                    books = fetchedBooks,
                    isLoading = false
                )

            } catch (e: Exception) {
                _uiState.value = _uiState.value?.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }

    fun onBookClicked(book: Book) {
        _events.value = HomeEvent.OpenBookDetails(
            book.id.lowercase()
        )
    }

    fun clearEvent() {
        _events.value = null
    }
}