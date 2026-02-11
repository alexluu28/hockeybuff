package com.example.hockeybuff.network

import com.example.hockeybuff.model.NewsItem
import com.example.hockeybuff.model.Score
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://site.api.espn.com/apis/site/v2/sports/hockey/nhl/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface HockeyApiService {
    @GET("scoreboard")
    suspend fun getScores(): ScoreboardResponse

    @GET("news")
    suspend fun getNews(): NewsResponse
}

object HockeyApi {
    val retrofitService: HockeyApiService by lazy {
        retrofit.create(HockeyApiService::class.java)
    }
}
