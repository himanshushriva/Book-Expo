package com.himan.bookexpo.data.repository

import com.himan.bookexpo.data.model.Book
import com.himan.bookexpo.data.model.BookDetails
import com.himan.bookexpo.data.remote.BookApi

class BookRepository(
    private val bookApi: BookApi
) {

    suspend fun getBooks(): List<Book> {
        //Fetch recent books from API
        val response = bookApi.getRecentBooks()

        if (response.isSuccessful) {
            val body = response.body()
                ?: throw Exception("Response body is null")

            throwIfStatusNotFound(body.status)

            return body.books
        } else {
            throw Exception("Request failed: ${response.code()}")
        }
    }

    suspend fun getBookDetails(id: String): BookDetails {
        //Fetch book details from API
        val response = bookApi.getBookDetails(id)

        if (response.isSuccessful) {
            val body = response.body()
                ?: throw Exception("Response body is null")

            throwIfStatusNotFound(body.status)

            return body
        } else {
            throw Exception("Request failed: ${response.code()}")
        }
    }

    private fun throwIfStatusNotFound(status: String) {
        if (status == "not found") {
            throw Exception("Book/s not found")
        }
    }
}