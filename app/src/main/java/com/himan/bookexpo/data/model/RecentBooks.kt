package com.himan.bookexpo.data.model

data class RecentBooks(
    val status: String,
    val total: Int,
    val books: List<Book>
)