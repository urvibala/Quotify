package com.example.practiceapp

import retrofit2.Response
import retrofit2.http.GET

interface QuoteApi {
    @GET("random")
    suspend fun getRandomQuote() : Response<QuoteModel>
}