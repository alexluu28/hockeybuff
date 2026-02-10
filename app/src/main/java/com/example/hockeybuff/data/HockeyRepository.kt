package com.example.hockeybuff.data

import com.example.hockeybuff.model.NewsItem
import com.example.hockeybuff.model.Score
import com.example.hockeybuff.network.HockeyApi
import com.example.hockeybuff.network.asNewsItems
import com.example.hockeybuff.network.asScores

class HockeyRepository {
    suspend fun getScores(): List<Score> {
        return HockeyApi.retrofitService.getScores().asScores()
    }

    suspend fun getNews(): List<NewsItem> {
        return HockeyApi.retrofitService.getNews().asNewsItems()
    }
}