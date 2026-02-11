package com.example.hockeybuff.ui.sharks

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.hockeybuff.model.NewsItem
import com.example.hockeybuff.model.OlympicEvent
import com.example.hockeybuff.model.Score
import com.example.hockeybuff.model.Team
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Composable
fun SharksScreen(
    sharksViewModel: SharksViewModel = viewModel(),
    onScoreClick: (Score) -> Unit
) {
    val uiState by sharksViewModel.uiState.collectAsState()

    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (uiState.error != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Error: ${uiState.error}", color = MaterialTheme.colorScheme.error)
        }
    } else {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            item {
                Text(
                    text = "hockeybuff",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            item {
                Text(text = "Scores", style = MaterialTheme.typography.headlineMedium)
            }
            items(uiState.scores.filter { it.homeTeam.name == "San Jose Sharks" || it.awayTeam.name == "San Jose Sharks" }) {
                ScoreCard(score = it, onScoreClick = onScoreClick)
            }
            item {
                Text(text = "2026 Winter Olympics", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(top = 16.dp))
            }
            items(uiState.olympicEvents) {
                OlympicEventCard(event = it)
            }
            item {
                Text(text = "News", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(top = 16.dp))
            }
            items(uiState.news) {
                NewsCard(newsItem = it)
            }
        }
    }
}

@Composable
fun OlympicEventCard(event: OlympicEvent) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = event.description ?: "", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Start: ${event.start_date ?: ""}", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(top = 4.dp))
            Text(text = "End: ${event.end_date ?: ""}", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(top = 4.dp))
        }
    }
}

@Composable
fun ScoreCard(score: Score, onScoreClick: (Score) -> Unit) {
    val dateFormat = remember { SimpleDateFormat("MMM d", Locale.getDefault()) }
    val timeFormat = remember { SimpleDateFormat("h:mm a", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("EST")
    } }
    val sharksAreHome = score.homeTeam.name == "San Jose Sharks"
    val opponentTeam = if (sharksAreHome) score.awayTeam else score.homeTeam
    val sharksTeam = if (sharksAreHome) score.homeTeam else score.awayTeam
    val opponentScore = if (sharksAreHome) score.awayScore else score.homeScore
    val sharksScore = if (sharksAreHome) score.homeScore else score.awayScore

    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).clickable { onScoreClick(score) },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = dateFormat.format(Date(score.date)),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = timeFormat.format(Date(score.date)),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    AsyncImage(
                        model = sharksTeam.logo,
                        contentDescription = "Sharks Logo",
                        modifier = Modifier.size(40.dp)
                    )
                    Text(text = sharksTeam.name, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
                }
                Text(text = "${sharksScore}", style = MaterialTheme.typography.headlineSmall)
                Text(text = "vs", style = MaterialTheme.typography.bodyMedium)
                Text(text = "${opponentScore}", style = MaterialTheme.typography.headlineSmall)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    AsyncImage(
                        model = opponentTeam.logo,
                        contentDescription = "Opponent Logo",
                        modifier = Modifier.size(40.dp)
                    )
                    Text(text = opponentTeam.name, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Composable
fun NewsCard(newsItem: NewsItem) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { Intent(Intent.ACTION_VIEW, Uri.parse(newsItem.url)).also { context.startActivity(it) } },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = newsItem.title, style = MaterialTheme.typography.bodyLarge)
            Text(text = newsItem.source ?: "", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(top = 4.dp))
        }
    }
}
