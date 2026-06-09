package com.himan.bookexpo.ui.home

sealed interface HomeEvent {

    data class OpenBookDetails(
        val bookId: String
    ) : HomeEvent
}