package com.himan.bookexpo.data.repository

import com.himan.bookexpo.data.model.Book
import com.himan.bookexpo.data.model.BookDetails
import com.himan.bookexpo.data.remote.BookApi
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class BookRepository(
    private val bookApi: BookApi
) {

    suspend fun getBooks(): List<Book> {
        //Fetch recent books from API
        try {

            val response = bookApi.getRecentBooks()

            if (!response.isSuccessful) {
                throw Exception("Request failed: ${response.code()}")
            }

            val body = response.body()
                ?: throw Exception("No Response")

            throwIfStatusNotFound(body.status)

            return body.books

        } catch (e: Exception) {
            throw mapNetworkException(e)
        }
    }

    suspend fun getBookDetails(id: String): BookDetails {
        //Fetch book details from API
        try {

            val response = bookApi.getBookDetails(id)

            if (!response.isSuccessful) {
                throw Exception("Request failed: ${response.code()}")
            }

            val body = response.body()
                ?: throw Exception("No Response")

            throwIfStatusNotFound(body.status)

            return body

        } catch (e: Exception) {
            throw mapNetworkException(e)
        }
    }

    private fun throwIfStatusNotFound(status: String) {
        if (status == "not found") {
            throw Exception("Book/s not found")
        }
    }

    private fun mapNetworkException(e: Exception): Exception {
        return when (e) {
            is UnknownHostException -> Exception("No internet connection")
            is SocketTimeoutException -> Exception("Request timed out")
            else -> e
        }
    }
}