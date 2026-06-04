package com.himan.bookexpo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.himan.bookexpo.data.repository.BookRepository

class HomeViewModelFactory(
    private val repository: BookRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        @Suppress("UNCHECKED_CAST")
        return HomeViewModel(repository) as T
    }
}