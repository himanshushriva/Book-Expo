package com.himan.bookexpo.data.model

data class BookDetails(
    val status: String,
    val id: String,
    val title: String,
    val subtitle: String,
    val description: String,
    val authors: String,
    val publisher: String,
    val pages: String,
    val year: String,
    val image: String,
    val url: String,
    val download: String
)