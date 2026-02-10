package com.example.hockeybuff.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Score(
    val homeTeam: Team,
    val awayTeam: Team,
    val homeScore: Int,
    val awayScore: Int,
    val gameState: String,
    val date: Long,
    val homeLinescores: List<Int>,
    val awayLinescores: List<Int>
) : Parcelable
