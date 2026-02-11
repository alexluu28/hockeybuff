package com.example.hockeybuff.data

import com.example.hockeybuff.model.OlympicEvent

object OlympicData {
    val events = listOf(
        OlympicEvent(
            id = "1",
            sportLabel = "ice_hockey",
            description = "Men's Preliminary Round - Group A",
            start_date = "2026-02-06T12:10:00Z",
            end_date = "2026-02-06T14:30:00Z"
        ),
        OlympicEvent(
            id = "2",
            sportLabel = "ice_hockey",
            description = "Men's Preliminary Round - Group B",
            start_date = "2026-02-06T16:40:00Z",
            end_date = "2026-02-06T19:00:00Z"
        ),
        OlympicEvent(
            id = "3",
            sportLabel = "ice_hockey",
            description = "Men's Preliminary Round - Group C",
            start_date = "2026-02-06T21:10:00Z",
            end_date = "2026-02-06T23:30:00Z"
        )
    )
}