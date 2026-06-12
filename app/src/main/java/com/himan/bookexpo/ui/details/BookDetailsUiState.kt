package com.himan.bookexpo.ui.details

import com.himan.bookexpo.data.model.BookDetails

data class BookDetailsUiState(
    val bookDetails: BookDetails? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)