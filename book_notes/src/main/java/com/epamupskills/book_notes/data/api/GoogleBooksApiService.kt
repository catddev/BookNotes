package com.epamupskills.book_notes.data.api

import com.epamupskills.book_notes.data.models.BookSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBooksApiService {

    @GET("v1/volumes")
    suspend fun getBooksByTitle(
        @Query("q") title: String,
        @Query("intitle:") inTitle: String = EMPTY_QUERY_PARAMETER,
        @Query("orderBy") orderBy: String = DEFAULT_ORDER_BY_PARAMETER,
    ): BookSearchResponse?

    companion object {
        private const val EMPTY_QUERY_PARAMETER = ""
        private const val DEFAULT_ORDER_BY_PARAMETER = "relevance"
    }
}