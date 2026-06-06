package com.himan.bookexpo.ui.home

import com.himan.bookexpo.data.model.Book

data class HomeUiState(
    val books: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)