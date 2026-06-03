package com.himan.bookexpo.data.repository

import com.himan.bookexpo.data.model.Book
import com.himan.bookexpo.data.remote.BookApi

class BookRepository(
    private val bookApi: BookApi
) {

    suspend fun getBooks(): List<Book> {
        //Fetch data from API
        val response = bookApi.getRecentBooks()

        if (response.isSuccessful) {
            val body = response.body()
                ?: throw Exception("Response body is null")

            return body.books
        } else {
            throw Exception("Request failed: ${response.code()}")
        }
    }
}