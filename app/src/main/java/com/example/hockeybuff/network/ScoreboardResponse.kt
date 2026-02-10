package com.example.hockeybuff.network

import com.example.hockeybuff.model.Score
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

data class ScoreboardResponse(val events: List<Event>)

data class Event(val date: String, val competitions: List<Competition>)

data class Competition(
    val competitors: List<Competitor>,
    val status: Status
)

data class Competitor(
    val team: Team,
    val score: String,
    val homeAway: String,
    val linescores: List<Linescore>?
)

data class Linescore(val value: Int)

data class Team(
    val displayName: String,
    val logo: String
)

data class Status(val type: StatusType)

data class StatusType(val name: String)

fun ScoreboardResponse.asScores(): List<Score> {
    return events.mapNotNull { event ->
        if (event.competitions.isNotEmpty()) {
            val competition = event.competitions[0]
            if (competition.competitors.size == 2) {
                val home = competition.competitors.find { it.homeAway == "home" }
                val away = competition.competitors.find { it.homeAway == "away" }
                if (home != null && away != null) {
                    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'", Locale.US).apply {
                        timeZone = TimeZone.getTimeZone("UTC")
                    }
                    val date = try {
                        format.parse(event.date)
                    } catch (e: Exception) {
                        null
                    }

                    if (date != null) {
                        Score(
                            homeTeam = com.example.hockeybuff.model.Team(home.team.displayName, home.team.logo),
                            awayTeam = com.example.hockeybuff.model.Team(away.team.displayName, away.team.logo),
                            homeScore = home.score.toIntOrNull() ?: 0,
                            awayScore = away.score.toIntOrNull() ?: 0,
                            gameState = competition.status.type.name,
                            date = date.time,
                            homeLinescores = home.linescores?.map { it.value } ?: emptyList(),
                            awayLinescores = away.linescores?.map { it.value } ?: emptyList()
                        )
                    } else {
                        null
                    }
                } else {
                    null
                }
            } else {
                null
            }
        } else {
            null
        }
    }
}
