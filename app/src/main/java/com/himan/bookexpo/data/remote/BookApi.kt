package com.himan.bookexpo.data.remote

import com.himan.bookexpo.data.model.RecentBooks
import retrofit2.Response
import retrofit2.http.GET

interface BookApi {

    //@GET("/recent")
    @GET("recent")
    suspend fun getRecentBooks(): Response<RecentBooks>

}