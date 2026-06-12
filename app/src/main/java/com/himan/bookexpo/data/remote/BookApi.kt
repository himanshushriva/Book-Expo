package com.himan.bookexpo.data.remote

import com.himan.bookexpo.data.model.BookDetails
import com.himan.bookexpo.data.model.RecentBooks
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BookApi {

    @GET("recent")
    suspend fun getRecentBooks(): Response<RecentBooks>

    @GET("book/{id}")
    suspend fun getBookDetails(
        @Path("id") id: String
    ): Response<BookDetails>
}