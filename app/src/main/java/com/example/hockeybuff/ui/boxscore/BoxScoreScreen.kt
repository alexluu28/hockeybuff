package com.example.hockeybuff.ui.boxscore

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hockeybuff.model.Score

@Composable
fun BoxScoreScreen(score: Score) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "${score.awayTeam.name} @ ${score.homeTeam.name}")
    }
}
