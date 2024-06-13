package com.epamupskills.booknotes.booknotes.data.api

import com.epamupskills.booknotes.booknotes.data.api.GoogleBooksApiSettings.BASE_QUERY_PARAMETER
import com.epamupskills.booknotes.booknotes.data.api.GoogleBooksApiSettings.DEFAULT_ORDER_BY_PARAMETER
import com.epamupskills.booknotes.booknotes.data.api.GoogleBooksApiSettings.GET_BOOKS_TITLE
import com.epamupskills.booknotes.booknotes.data.api.GoogleBooksApiSettings.ORDER_QUERY_PARAMETER
import com.epamupskills.booknotes.booknotes.data.api.GoogleBooksApiSettings.SEARCH_IN_TITLE_QUERY_PARAMETER
import com.epamupskills.booknotes.booknotes.data.models.BookSearchResponse
import com.epamupskills.booknotes.core.Constants.EMPTY
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBooksApi {

    @GET(GET_BOOKS_TITLE)
    suspend fun getBooksByTitle(
        @Query(BASE_QUERY_PARAMETER) title: String,
        @Query(SEARCH_IN_TITLE_QUERY_PARAMETER) inTitle: String = EMPTY,
        @Query(ORDER_QUERY_PARAMETER) orderBy: String = DEFAULT_ORDER_BY_PARAMETER,
    ): BookSearchResponse?
}