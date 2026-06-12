package com.himan.bookexpo.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.himan.bookexpo.data.repository.BookRepository
import kotlinx.coroutines.launch

class BookDetailsViewModel(
    private val repository: BookRepository
) : ViewModel() {

    private val _uiState = MutableLiveData(BookDetailsUiState())
    val uiState: LiveData<BookDetailsUiState> = _uiState

    fun loadBookDetails(id: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value?.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val bookDetails = repository.getBookDetails(id)
                _uiState.value = BookDetailsUiState(
                    bookDetails = bookDetails,
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
}